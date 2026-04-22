package org.shtiroy.module1.hm04.hikari.mapper;

import org.shtiroy.module1.hm04.hikari.dto.ProductResponseDto;
import org.shtiroy.module1.hm04.hikari.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductResponseDto toDto(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getProductType(),
                product.getUserId()
        );
    }
}
