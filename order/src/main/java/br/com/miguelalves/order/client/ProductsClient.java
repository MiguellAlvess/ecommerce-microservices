package br.com.miguelalves.order.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.miguelalves.order.client.represetation.ProductRepresetation;

@FeignClient(name = "products", url = "${ecommerce.orders.clients.products.url}")
public interface ProductsClient {

    @GetMapping
    ResponseEntity<List<ProductRepresetation>> getProductById(@PathVariable("id") Long id);
}
