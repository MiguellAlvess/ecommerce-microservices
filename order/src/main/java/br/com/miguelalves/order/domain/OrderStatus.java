package br.com.miguelalves.order.domain;

public enum OrderStatus {
    PLACED,
    PAID,
    INVOICED,
    SHIPPED,
    PAYMENT_ERROR,
    PREPARING_SHIPMENT
}
