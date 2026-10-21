package br.com.miguelalves.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.miguelalves.order.client.BankingServiceClient;
import br.com.miguelalves.order.domain.Order;
import br.com.miguelalves.order.dto.CreateOrderDTO;
import br.com.miguelalves.order.mapper.OrderMapper;
import br.com.miguelalves.order.exception.OrderNotFoundException;
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

}
