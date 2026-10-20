package br.com.miguelalves.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import br.com.miguelalves.order.domain.OrderStatus;

public record OrderDTO(
        Long id,
        Long customerId,
        LocalDateTime orderDate,
        String paymentKey,
        String notes,
        OrderStatus status,
        BigDecimal total,
        String trackingCode,
        String invoiceUrl,
        List<OrderItemResponseDTO> items) {
}
