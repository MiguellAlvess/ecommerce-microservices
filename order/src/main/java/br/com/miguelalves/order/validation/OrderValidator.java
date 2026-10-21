package br.com.miguelalves.order.validation;

import java.util.List;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.miguelalves.order.client.CustomersClient;
import br.com.miguelalves.order.client.ProductsClient;
import br.com.miguelalves.order.domain.Order;
import br.com.miguelalves.order.domain.OrderItem;
import br.com.miguelalves.order.dto.OrderItemDTO;

@Component
public class OrderValidator {

    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);

    private final ProductsClient productsClient;
    private final CustomersClient customersClient;

    public OrderValidator(ProductsClient productsClient, CustomersClient customersClient) {
        this.productsClient = productsClient;
        this.customersClient = customersClient;
    }

    public void validate(Order order) {
        Long customerId = order.getCustomerId();
        validateCustomer(customerId);
        List<OrderItem> items = order.getItems();
        for (OrderItem item : items) {
            validateItem(item);
        }

    }

    private void validateCustomer(Long customerId) {
        var customerResponse = customersClient.getCustomerById(customerId);
        if (!customerResponse.getStatusCode().is2xxSuccessful()) {
            throw new IllegalArgumentException("Customer not found with ID: " + customerId);
        }
        log.info("Customer with ID {} is valid.", customerId);
    }

    private void validateItem(OrderItem item) {
        Long productId = item.getProductId();
        var productResponse = productsClient.getProductById(productId);
        if (!productResponse.getStatusCode().is2xxSuccessful() || productResponse.getBody() == null
                || productResponse.getBody().isEmpty()) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
        log.info("Product with ID {} is valid.", productId);
    }

}
