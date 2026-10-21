package br.com.miguelalves.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.miguelalves.order.dto.ReceiptPaymentCallbackDTO;
import br.com.miguelalves.order.service.OrderService;

@RestController
@RequestMapping("/api/orders/payment-callback")
public class ReceiptPaymentCallbackController {

    private OrderService orderService;

    public ReceiptPaymentCallbackController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Object> updatePaymentSatus(@RequestBody ReceiptPaymentCallbackDTO dto,
            @RequestHeader(required = true, name = "apiKey") String apiKey) {
        orderService.updatePaymentStatus(
                dto.id(),
                dto.paymentKey(),
                dto.status(),
                dto.notes());
        return ResponseEntity.ok().build();
    }
}
