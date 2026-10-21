package br.com.miguelalves.order.dto;

import br.com.miguelalves.order.domain.PaymentMethod;

public record CreateNewPaymentDTO(
        Long orderId,
        String cardData,
        PaymentMethod paymentMethod) {

}
