package br.com.miguelalves.order.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.miguelalves.order.dto.CreateNewPaymentDTO;
import br.com.miguelalves.order.dto.CreateOrderDTO;
import br.com.miguelalves.order.dto.OrderDTO;
import br.com.miguelalves.order.dto.PaymentDataDTO;
import br.com.miguelalves.order.mapper.OrderMapper;
import br.com.miguelalves.order.domain.Order;

import br.com.miguelalves.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody CreateOrderDTO dto) {
        Order order = orderService.createOrder(orderMapper.toEntity(dto));
        OrderDTO response = orderMapper.toResponseDTO(order);
        return ResponseEntity.created(URI.create("/api/orders/" + order.getId())).body(response);
    }

    @PostMapping("/payments")
    public ResponseEntity<Object> addNewPayment(@RequestBody CreateNewPaymentDTO dto) {
        orderService.addNewPayment(dto.orderId(), dto.cardData(), dto.paymentMethod());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> findById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(orderMapper.toResponseDTO(order));
    }

}
