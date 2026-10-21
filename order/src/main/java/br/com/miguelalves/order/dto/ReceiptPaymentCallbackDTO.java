package br.com.miguelalves.order.dto;

public record ReceiptPaymentCallbackDTO(
        Long id,
        String paymentKey,
        Boolean status,
        String notes) {

}
