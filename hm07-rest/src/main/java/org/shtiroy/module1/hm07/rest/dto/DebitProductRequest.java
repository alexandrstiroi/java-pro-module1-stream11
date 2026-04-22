package org.shtiroy.module1.hm07.rest.dto;

import java.math.BigDecimal;

public record DebitProductRequest(BigDecimal amount, Long userId) {
}
