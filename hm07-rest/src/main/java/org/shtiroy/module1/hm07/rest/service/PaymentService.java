package org.shtiroy.module1.hm07.rest.service;

import org.shtiroy.module1.hm07.rest.dto.ExecutePaymentRequest;
import org.shtiroy.module1.hm07.rest.dto.PaymentExecutionResponse;
import org.shtiroy.module1.hm07.rest.dto.ProductResponseDto;
import org.shtiroy.module1.hm07.rest.exception.BadRequestException;
import org.shtiroy.module1.hm07.rest.exception.InsufficientFundsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {
    private final ProductClient productClient;

    public PaymentService(ProductClient productServiceClient) {
        this.productClient = productServiceClient;
    }

    public List<ProductResponseDto> getProductsByUserId(Long userId) {
        return productClient.getProductsByUserId(userId);
    }

    public PaymentExecutionResponse execute(ExecutePaymentRequest request) {
        ProductResponseDto product = productClient.getProductById(request.productId());

        if (!product.userId().equals(request.userId())) {
            throw new BadRequestException("Выбранный товар не принадлежит пользователю id=" + request.userId());
        }

        if (product.balance().compareTo(request.amount()) < 0) {
            throw new InsufficientFundsException("Недостаточно средств id=" + request.productId());
        }

        ProductResponseDto debitedProduct = productClient.debitProduct(
                request.productId(),
                request.userId(),
                request.amount()
        );

        return new PaymentExecutionResponse(
                "SUCCESS",
                request.userId(),
                request.productId(),
                request.amount(),
                debitedProduct.balance()
        );
    }
}
