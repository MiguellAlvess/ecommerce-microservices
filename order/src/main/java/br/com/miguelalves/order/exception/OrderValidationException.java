package br.com.miguelalves.order.exception;

public class OrderValidationException extends RuntimeException {

    private final String code;

    public OrderValidationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
