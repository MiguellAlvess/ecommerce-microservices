package br.com.miguelalves.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.miguelalves.order.client.BankingServiceClient;
import br.com.miguelalves.order.domain.Order;
import br.com.miguelalves.order.domain.OrderStatus;
import br.com.miguelalves.order.domain.PaymentData;
import br.com.miguelalves.order.domain.PaymentMethod;
import br.com.miguelalves.order.mapper.OrderMapper;
import br.com.miguelalves.order.exception.OrderNotFoundException;
import br.com.miguelalves.order.exception.OrderValidationException;
import br.com.miguelalves.order.repository.OrderItemRepository;
import br.com.miguelalves.order.repository.OrderRepository;
import br.com.miguelalves.order.validation.OrderValidator;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderItemRepository orderItemRepository;
    private final BankingServiceClient bankingServiceClient;

    public OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderMapper orderMapper,
            BankingServiceClient bankingServiceClient, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderItemRepository = orderItemRepository;
        this.bankingServiceClient = bankingServiceClient;
    }

    @Transactional
    public Order createOrder(Order order) {
        orderValidator.validate(order);
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getItems());
        String paymentKey = bankingServiceClient.paymentRequest(order);
        order.setPaymentKey(paymentKey);
        return order;
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public void updatePaymentStatus(Long id, String paymentKey, Boolean status, String notes) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        if (!order.getPaymentKey().equals(paymentKey)) {
            throw new IllegalArgumentException("Chave de pagamento inválida para o pedido " + id);
        }
        if (!status) {
            order.setStatus(OrderStatus.PAYMENT_ERROR);
            order.setNotes(notes);
        }
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

    @Transactional
    public void addNewPayment(Long orderId, String cardData, PaymentMethod paymentMethod) {
        if (orderId == null || orderId <= 0) {
            throw new OrderValidationException(
                    "INVALID_ORDER_ID",
                    "O identificador do pedido deve ser maior que zero");
        }

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        if (cardData == null || cardData.isBlank()) {
            throw new OrderValidationException(
                    "INVALID_PAYMENT_DATA",
                    "Os dados do cartão são obrigatórios");
        }
        if (paymentMethod == null) {
            throw new OrderValidationException(
                    "INVALID_PAYMENT_METHOD",
                    "O método de pagamento é obrigatório");
        }
        PaymentData paymentData = new PaymentData(cardData, paymentMethod);
        order.setPaymentData(paymentData);
        order.setStatus(OrderStatus.PLACED);
        order.setNotes("Novo pagamento realizado, aguardando aprovação do pagamento.");
        String newPaymentKey = bankingServiceClient.paymentRequest(order);
        order.setPaymentKey(newPaymentKey);
        orderRepository.save(order);

    }

}
