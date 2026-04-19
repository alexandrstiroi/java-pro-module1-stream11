package org.shtiroy.module1.hm04.hikari.dto;

import org.shtiroy.module1.hm04.hikari.entity.Product;
import org.shtiroy.module1.hm04.hikari.entity.ProductType;

import java.math.BigDecimal;

public record ProductResponseDto(Long id, String accountNumber, BigDecimal balance, ProductType productType, Long userId) {

    public static ProductResponseDto from(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getProductType(),
                product.getUserId()
        );
    }
}
