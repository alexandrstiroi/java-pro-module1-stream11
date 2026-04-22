package org.shtiroy.module1.hm07.rest.service;

import org.shtiroy.module1.hm07.rest.dto.DebitProductRequest;
import org.shtiroy.module1.hm07.rest.dto.ExecutePaymentRequest;
import org.shtiroy.module1.hm07.rest.dto.PaymentExecutionResponse;
import org.shtiroy.module1.hm07.rest.dto.ProductResponseDto;
import org.shtiroy.module1.hm07.rest.exception.BadRequestException;
import org.shtiroy.module1.hm07.rest.exception.InsufficientFundsException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentService {
    private final RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductResponseDto> getProductsByUserId(Long userId) {
        ResponseEntity<ProductResponseDto[]> response = restTemplate.getForEntity(
                "/user/" + userId,
                ProductResponseDto[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public PaymentExecutionResponse execute(ExecutePaymentRequest request) {
        ProductResponseDto product = restTemplate.getForObject("/products/"+request.productId(), ProductResponseDto.class);

        if (product != null && !product.userId().equals(request.userId())) {
            throw new BadRequestException("Выбранный товар не принадлежит пользователю id=" + request.userId());
        }

        if (product != null && product.balance().compareTo(request.amount()) < 0) {
            throw new InsufficientFundsException("Недостаточно средств id=" + request.productId());
        }
        DebitProductRequest requestBody = new DebitProductRequest(request.amount(), request.userId());
        ProductResponseDto debitedProduct = restTemplate.postForObject("/" + product.id() + "/debit", requestBody,  ProductResponseDto.class);

        return new PaymentExecutionResponse(
                "SUCCESS",
                request.userId(),
                request.productId(),
                request.amount(),
                debitedProduct.balance()
        );
    }
}
