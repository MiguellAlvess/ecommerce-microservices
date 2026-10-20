package br.com.miguelalves.order.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import br.com.miguelalves.order.domain.Order;
import br.com.miguelalves.order.dto.CreateOrderDTO;
import br.com.miguelalves.order.mapper.OrderMapper;
import br.com.miguelalves.order.repository.OrderRepository;
import br.com.miguelalves.order.validation.OrderValidator;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderValidator orderValidator, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderValidator = orderValidator;
        this.orderMapper = orderMapper;
    }

    @Transactional
    public Order createOrder(CreateOrderDTO dto) {
        orderValidator.validate(dto);
        Order order = orderMapper.toEntity(dto);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pedido não encontrado: " + id));
    }

}
