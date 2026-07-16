package br.com.miguelalves.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.miguelalves.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
