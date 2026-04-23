package org.shtiroy.module1.hm04.hikari.dto;

import java.math.BigDecimal;

public record DebitProductRequest(BigDecimal amount, Long userId) {

}
