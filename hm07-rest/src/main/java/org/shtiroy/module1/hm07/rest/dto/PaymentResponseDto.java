package org.shtiroy.module1.hm07.rest.dto;

import java.math.BigDecimal;

public record PaymentResponseDto(boolean success, String message, BigDecimal newBalance) {

}