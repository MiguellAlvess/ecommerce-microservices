package br.com.miguelalves.order.exception;

public class CustomerNotFoundException extends OrderValidationException {

    public CustomerNotFoundException(Long customerId) {
        super("CUSTOMER_NOT_FOUND", "Cliente não encontrado: " + customerId);
    }
}
