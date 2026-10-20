package br.com.miguelalves.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.miguelalves.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
