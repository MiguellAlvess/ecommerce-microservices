package br.com.miguelalves.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.miguelalves.customer.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
