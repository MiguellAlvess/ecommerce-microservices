package br.com.miguelalves.order.validation;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.miguelalves.order.client.ProductsClient;
import br.com.miguelalves.order.domain.Order;
import br.com.miguelalves.order.dto.CreateOrderDTO;
import br.com.miguelalves.order.dto.OrderItemDTO;

@Component
public class OrderValidator {

    private final ProductsClient productsClient;

    public OrderValidator(ProductsClient productsClient) {
        this.productsClient = productsClient;
    }

    public void validate(Order order) {

    }

}
