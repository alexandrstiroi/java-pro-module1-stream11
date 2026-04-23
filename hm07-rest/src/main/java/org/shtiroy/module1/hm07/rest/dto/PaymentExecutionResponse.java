package org.shtiroy.module1.hm07.rest.dto;

import java.math.BigDecimal;

public record PaymentExecutionResponse(String status,
                                       Long userId,
                                       Long productId,
                                       BigDecimal debitedAmount,
                                       BigDecimal remainingBalance) {
}
