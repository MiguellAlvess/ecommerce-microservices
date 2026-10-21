package br.com.miguelalves.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.miguelalves.order.client.represetation.CustomerRepresentation;

@FeignClient(name = "customers", url = "${ecommerce.orders.clients.customers.url}")
public interface CustomersClient {

    @GetMapping("{id}")
    ResponseEntity<CustomerRepresentation> getCustomerById(@PathVariable("id") Long id);
}
