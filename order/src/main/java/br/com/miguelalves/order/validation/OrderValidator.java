package br.com.miguelalves.order.validation;

import java.util.List;

import org.springframework.stereotype.Component;

import br.com.miguelalves.order.client.CustomersClient;
import br.com.miguelalves.order.client.ProductsClient;
import br.com.miguelalves.order.domain.Order;
import br.com.miguelalves.order.dto.CreateOrderDTO;
import br.com.miguelalves.order.dto.OrderItemDTO;

@Component
public class OrderValidator {

    private final ProductsClient productsClient;
    private final CustomersClient customersClient;

    public OrderValidator(ProductsClient productsClient, CustomersClient customersClient) {
        this.productsClient = productsClient;
        this.customersClient = customersClient;
    }

    public void validate(Order order) {
        Long customerId = order.getCustomerId();
        validateCustomer(customerId);

    }

    private void validateCustomer(Long customerId) {
        var customerResponse = customersClient.getCustomerById(customerId);
        if (!customerResponse.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }
    }

    private void validateProducts(List<OrderItemDTO> orderItems) {
        for (OrderItemDTO item : orderItems) {
            var productResponse = productsClient.getProductById(item.productId());
            if (!productResponse.getStatusCode().is2xxSuccessful()) {
                throw new IllegalArgumentException("Product not found with ID: " + item.productId());
            }
        }
    }

}
