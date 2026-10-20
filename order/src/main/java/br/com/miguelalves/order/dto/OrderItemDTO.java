package br.com.miguelalves.order.dto;

import java.math.BigDecimal;

public record OrderItemDTO(
        Long productId,
        Integer quantity,
        BigDecimal unitPrice) {

}
