package br.com.miguelalves.order.exception;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(Long orderId) {
        super("Pedido não encontrado: " + orderId);
    }
}
