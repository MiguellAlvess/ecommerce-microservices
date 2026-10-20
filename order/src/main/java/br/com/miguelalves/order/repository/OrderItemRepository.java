package br.com.miguelalves.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.miguelalves.order.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
