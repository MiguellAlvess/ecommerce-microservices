package br.com.miguelalves.order.exception;

public class ProductNotFoundException extends OrderValidationException {

    public ProductNotFoundException(Long productId) {
        super("PRODUCT_NOT_FOUND", "Produto não encontrado: " + productId);
    }
}
