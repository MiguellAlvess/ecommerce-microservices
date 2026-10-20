package br.com.miguelalves.order.validation;

import org.springframework.stereotype.Component;

import br.com.miguelalves.order.dto.CreateOrderDTO;
import br.com.miguelalves.order.dto.OrderItemDTO;

@Component
public class OrderValidator {

    public void validate(CreateOrderDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Os dados do pedido são obrigatórios");
        }
        if (dto.customerId() == null || dto.customerId() <= 0) {
            throw new IllegalArgumentException("O cliente do pedido é obrigatório");
        }
        if (dto.paymentData() == null || dto.paymentData().paymentMethod() == null) {
            throw new IllegalArgumentException("Os dados de pagamento são obrigatórios");
        }
        if (dto.paymentData().data() == null || dto.paymentData().data().isBlank()) {
            throw new IllegalArgumentException("A identificação do pagamento é obrigatória");
        }
        if (dto.items() == null || dto.items().isEmpty()) {
            throw new IllegalArgumentException("O pedido deve possuir ao menos um item");
        }

        for (OrderItemDTO item : dto.items()) {
            validateItem(item);
        }
    }

    private void validateItem(OrderItemDTO item) {
        if (item == null) {
            throw new IllegalArgumentException("Os itens do pedido não podem ser nulos");
        }
        if (item.productId() == null || item.productId() <= 0) {
            throw new IllegalArgumentException("O produto do item é obrigatório");
        }
        if (item.quantity() == null || item.quantity() <= 0) {
            throw new IllegalArgumentException("A quantidade do item deve ser maior que zero");
        }
        if (item.unitPrice() == null || item.unitPrice().signum() < 0) {
            throw new IllegalArgumentException("O preço unitário não pode ser negativo");
        }
    }

}
