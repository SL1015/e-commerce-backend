package com.petcove.orderservice.event;
import com.petcove.orderservice.model.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent implements Serializable {
    private Long paymentId;
    private String orderNumber;
    private Long customerId;
    private PaymentStatus paymentStatus;
}