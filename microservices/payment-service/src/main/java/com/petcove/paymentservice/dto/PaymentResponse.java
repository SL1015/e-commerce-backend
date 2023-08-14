package com.petcove.paymentservice.dto;

import com.petcove.paymentservice.model.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class PaymentResponse implements Serializable {
    //private Long id;

    private String orderNumber;

    private Long customerId;

    private BigDecimal totalAmount;

    private PaymentStatus paymentStatus;
}