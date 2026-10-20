package br.com.miguelalves.order.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import br.com.miguelalves.order.domain.Order;
import br.com.miguelalves.order.domain.OrderItem;
import br.com.miguelalves.order.domain.OrderStatus;
import br.com.miguelalves.order.domain.PaymentData;
import br.com.miguelalves.order.dto.CreateOrderDTO;
import br.com.miguelalves.order.dto.OrderDTO;
import br.com.miguelalves.order.dto.OrderItemDTO;
import br.com.miguelalves.order.dto.OrderItemResponseDTO;
import br.com.miguelalves.order.dto.PaymentDataDTO;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderDate", ignore = true)
    @Mapping(target = "paymentKey", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "trackingCode", ignore = true)
    @Mapping(target = "invoiceUrl", ignore = true)
    Order toEntity(CreateOrderDTO dto);

    OrderDTO toResponseDTO(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "order", ignore = true)
    OrderItem toEntity(OrderItemDTO dto);

    OrderItemResponseDTO toResponseDTO(OrderItem orderItem);

    PaymentData toEntity(PaymentDataDTO dto);

    @AfterMapping
    default void afterMapping(@MappingTarget Order order) {
        order.setStatus(OrderStatus.PLACED);
        order.setOrderDate(LocalDateTime.now());
        order.setTotal(calculateTotal(order));
        order.getItems().forEach(item -> item.setOrder(order));
    }

    private static BigDecimal calculateTotal(Order order) {
        return order.getItems().stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
