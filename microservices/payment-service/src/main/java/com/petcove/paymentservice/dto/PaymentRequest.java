package com.petcove.paymentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor

public class PaymentRequest {
    private String orderNumber;
    private Long customerId;
    private BigDecimal totalAmount;
}
