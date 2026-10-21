package br.com.miguelalves.order.client;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import br.com.miguelalves.order.domain.Order;

@Component
public class BankingServiceClient {

    private static final Logger log = LoggerFactory.getLogger(BankingServiceClient.class);

    public String paymentRequest(Order order) {
        log.info("Payment request for order {}", order.getId());
        return UUID.randomUUID().toString();
    }
}
