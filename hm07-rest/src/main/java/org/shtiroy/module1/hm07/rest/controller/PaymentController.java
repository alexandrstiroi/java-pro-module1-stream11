package org.shtiroy.module1.hm07.rest.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.shtiroy.module1.hm07.rest.dto.ExecutePaymentRequest;
import org.shtiroy.module1.hm07.rest.dto.PaymentExecutionResponse;
import org.shtiroy.module1.hm07.rest.dto.ProductResponseDto;
import org.shtiroy.module1.hm07.rest.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private static final Logger log = LogManager.getLogger(PaymentController.class);

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/products/{userId}")
    public List<ProductResponseDto> getProducts(@PathVariable Long userId) {
        log.info("Поступил запрос на все продукты пользователя {}", userId);
        return paymentService.getProductsByUserId(userId);
    }

    @PostMapping("/execute")
    @ResponseStatus(HttpStatus.OK)
    public PaymentExecutionResponse execute(@RequestBody ExecutePaymentRequest request) {
        return paymentService.execute(request);
    }
}
