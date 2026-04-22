package org.shtiroy.module1.hm04.hikari.service;

import org.shtiroy.module1.hm04.hikari.dto.DebitProductRequest;
import org.shtiroy.module1.hm04.hikari.dto.ProductResponseDto;
import org.shtiroy.module1.hm04.hikari.entity.Product;
import org.shtiroy.module1.hm04.hikari.exception.BadRequestException;
import org.shtiroy.module1.hm04.hikari.exception.InsufficientFundsException;
import org.shtiroy.module1.hm04.hikari.exception.NotFoundException;
import org.shtiroy.module1.hm04.hikari.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .map(this::from)
                .toList();
    }

    public Optional<ProductResponseDto> getProductById(Long productId) {
        return productRepository.findById(productId).map(this::from);
    }

    @Transactional
    public ProductResponseDto debit(Long productId, DebitProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Продукт с id="+productId+" не найден"));

        if (!product.getUserId().equals(request.userId())) {
            throw new BadRequestException("Выбранный товар не принадлежит пользователю id=" + request.userId());
        }

        if (product.getBalance().compareTo(request.amount()) < 0) {
            throw new InsufficientFundsException("Недостаточно средств id=" + productId);
        }

        product.setBalance(product.getBalance().subtract(request.amount()));
        return from(productRepository.save(product));
    }

    private ProductResponseDto from(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getAccountNumber(),
                product.getBalance(),
                product.getProductType(),
                product.getUserId()
        );
    }
}
