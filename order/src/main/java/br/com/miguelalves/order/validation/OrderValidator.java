package br.com.miguelalves.order.validation;

import org.springframework.stereotype.Component;

import br.com.miguelalves.order.domain.Order;
import br.com.miguelalves.order.dto.CreateOrderDTO;
import br.com.miguelalves.order.dto.OrderItemDTO;

@Component
public class OrderValidator {

    public void validate(Order order) {

        if (order.getItems().stream().anyMatch(item -> item.getQuantity() <= 0)) {
            throw new IllegalArgumentException("Invalid quantity");
        }

    }

}
