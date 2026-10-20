package br.com.miguelalves.order.dto;

import br.com.miguelalves.order.domain.PaymentMethod;

public record PaymentDataDTO(
        String data,
        PaymentMethod paymentMethod) {

}
