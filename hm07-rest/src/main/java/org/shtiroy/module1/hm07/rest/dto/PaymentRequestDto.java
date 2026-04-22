package org.shtiroy.module1.hm07.rest.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(Long userId, Long productId,BigDecimal amount) {

}
