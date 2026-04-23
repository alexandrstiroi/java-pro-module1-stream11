package org.shtiroy.module1.hm04.hikari.controller;

import org.shtiroy.module1.hm04.hikari.dto.DebitProductRequest;
import org.shtiroy.module1.hm04.hikari.dto.ProductResponseDto;
import org.shtiroy.module1.hm04.hikari.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/user/{userId}")
    public List<ProductResponseDto> getByUserId(@PathVariable Long userId) {
        return productService.getProductsByUserId(userId);
    }

    @GetMapping("/{productId}")
    public ProductResponseDto getById(@PathVariable Long productId) {
        return productService.getProductById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Продукт не найден"));
    }

    @PostMapping("/{productId}/debit")
    public ProductResponseDto debit(@PathVariable Long productId, @RequestBody DebitProductRequest request) {
        return productService.debit(productId, request);
    }
}
