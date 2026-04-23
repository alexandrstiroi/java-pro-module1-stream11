package org.shtiroy.module1.hm07.rest.dto;

import java.math.BigDecimal;

public record ProductResponseDto(Long id, String accountNumber, BigDecimal balance, ProductType productType, Long userId) {
}
