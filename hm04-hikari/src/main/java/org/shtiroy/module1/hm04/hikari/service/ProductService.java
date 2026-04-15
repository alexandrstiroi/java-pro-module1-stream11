package org.shtiroy.module1.hm04.hikari.service;

import org.shtiroy.module1.hm04.hikari.dto.ProductResponseDto;
import org.shtiroy.module1.hm04.hikari.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> getProductsByUserId(Long userId) {
        return productRepository.findByUserId(userId).stream()
                .map(ProductResponseDto::from)
                .toList();
    }

    public Optional<ProductResponseDto> getProductById(Long productId) {
        return productRepository.findById(productId).map(ProductResponseDto::from);
    }
}
