package br.com.miguelalves.order.validation;

import java.util.List;

import feign.FeignException;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.miguelalves.order.client.CustomersClient;
import br.com.miguelalves.order.client.ProductsClient;
import br.com.miguelalves.order.domain.Order;
import br.com.miguelalves.order.domain.OrderItem;
import br.com.miguelalves.order.exception.CustomerNotFoundException;
import br.com.miguelalves.order.exception.ExternalServiceException;
import br.com.miguelalves.order.exception.OrderValidationException;
import br.com.miguelalves.order.exception.ProductNotFoundException;

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
        validateRequiredData(order);
        Long customerId = order.getCustomerId();
        validateCustomer(customerId);
        List<OrderItem> items = order.getItems();
        for (OrderItem item : items) {
            validateItem(item);
        }

    }

    private void validateCustomer(Long customerId) {
        try {
            var response = customersClient.getCustomerById(customerId);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new CustomerNotFoundException(customerId);
            }
        } catch (FeignException.NotFound exception) {
            throw new CustomerNotFoundException(customerId);
        } catch (FeignException exception) {
            throw new ExternalServiceException("customers", exception);
        }

        log.info("Customer with ID {} is valid.", customerId);
    }

    private void validateItem(OrderItem item) {
        Long productId = item.getProductId();
        try {
            var response = productsClient.getProductById(productId);
            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null
                    || response.getBody().isEmpty()) {
                throw new ProductNotFoundException(productId);
            }
        } catch (FeignException.NotFound exception) {
            throw new ProductNotFoundException(productId);
        } catch (FeignException exception) {
            throw new ExternalServiceException("products", exception);
        }

        log.info("Product with ID {} is valid.", productId);
    }

    private void validateRequiredData(Order order) {
        if (order == null) {
            throw invalid("ORDER_REQUIRED", "Os dados do pedido são obrigatórios");
        }
        if (order.getCustomerId() == null || order.getCustomerId() <= 0) {
            throw invalid("INVALID_CUSTOMER_ID", "O identificador do cliente deve ser maior que zero");
        }
        if (order.getPaymentData() == null || order.getPaymentData().paymentMethod() == null
                || order.getPaymentData().data() == null || order.getPaymentData().data().isBlank()) {
            throw invalid("INVALID_PAYMENT_DATA", "Os dados de pagamento são obrigatórios");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw invalid("ORDER_ITEMS_REQUIRED", "O pedido deve possuir ao menos um item");
        }

        for (OrderItem item : order.getItems()) {
            validateRequiredItemData(item);
        }
    }

    private void validateRequiredItemData(OrderItem item) {
        if (item == null) {
            throw invalid("INVALID_ORDER_ITEM", "Os itens do pedido não podem ser nulos");
        }
        if (item.getProductId() == null || item.getProductId() <= 0) {
            throw invalid("INVALID_PRODUCT_ID", "O identificador do produto deve ser maior que zero");
        }
        if (item.getQuantity() == null || item.getQuantity() <= 0) {
            throw invalid("INVALID_QUANTITY", "A quantidade do item deve ser maior que zero");
        }
        if (item.getUnitPrice() == null || item.getUnitPrice().signum() < 0) {
            throw invalid("INVALID_UNIT_PRICE", "O preço unitário não pode ser negativo");
        }
    }

    private OrderValidationException invalid(String code, String message) {
        return new OrderValidationException(code, message);
    }

}
