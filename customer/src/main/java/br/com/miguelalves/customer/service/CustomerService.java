package br.com.miguelalves.customer.service;

import org.springframework.stereotype.Service;

import br.com.miguelalves.customer.domain.Customer;
import br.com.miguelalves.customer.repository.CustomerRepository;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer findById(Long id) {
        return customerRepository.findById(id).orElse(null);
    }

}
