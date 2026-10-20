package br.com.miguelalves.order.dto;

import java.math.BigDecimal;

public record OrderItemResponseDTO(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal unitPrice) {
}
