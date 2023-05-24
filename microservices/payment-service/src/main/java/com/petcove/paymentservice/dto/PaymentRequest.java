package com.petcove.paymentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor

public class PaymentRequest {
    @NotBlank(message = "OrderNumber is required")
    private String orderNumber;
    @NotNull(message = "CustomerId is required.")
    private Long customerId;
    @NotNull(message = "Payment amount is required.")
    private BigDecimal totalAmount;
}
