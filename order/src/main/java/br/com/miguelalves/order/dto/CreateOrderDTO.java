package br.com.miguelalves.order.dto;

import java.util.List;

public record CreateOrderDTO(
        Long customerId,
        String notes,
        PaymentDataDTO paymentData,
        List<OrderItemDTO> items) {

}
