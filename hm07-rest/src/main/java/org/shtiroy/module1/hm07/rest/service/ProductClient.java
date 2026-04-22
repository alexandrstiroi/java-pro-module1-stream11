package org.shtiroy.module1.hm07.rest.service;

import org.shtiroy.module1.hm07.rest.dto.DebitProductRequest;
import org.shtiroy.module1.hm07.rest.dto.ProductResponseDto;
import org.shtiroy.module1.hm07.rest.dto.RemoteApiErrorResponse;
import org.shtiroy.module1.hm07.rest.exception.RemoteServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ProductClient {
    private final RestTemplate restTemplate;

    public ProductClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProductResponseDto> getProductsByUserId(Long userId) {
        try {

            ResponseEntity<ProductResponseDto[]> response = restTemplate.getForEntity(
                    "/user/" + userId,
                    ProductResponseDto[].class);
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } catch (ResourceAccessException exception) {
            throw productServiceUnavailable(exception);
        }
    }

    public ProductResponseDto getProductById(Long productId) {
        try {
            return restTemplate.getForObject("/products/"+productId, ProductResponseDto.class);
        } catch (ResourceAccessException exception) {
            throw productServiceUnavailable(exception);
        }
    }

    public ProductResponseDto debitProduct(Long productId, Long userId, BigDecimal amount) {
        DebitProductRequest requestBody = new DebitProductRequest(amount, userId);
        try {
            return restTemplate.postForObject("/" + productId + "/debit", requestBody,  ProductResponseDto.class);
        } catch (ResourceAccessException exception) {
            throw productServiceUnavailable(exception);
        }
    }

    private RemoteServiceException productServiceUnavailable(Exception exception) {
        RemoteApiErrorResponse errorResponse = new RemoteApiErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_GATEWAY.value(),
                HttpStatus.BAD_GATEWAY.getReasonPhrase(),
                "product-service",
                List.of("Продуктовый сервис недоступен: " + exception.getMessage())
        );
        return new RemoteServiceException(HttpStatus.BAD_GATEWAY, errorResponse);
    }
}
