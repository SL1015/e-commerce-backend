package com.petcove.paymentservice.model.adapter;

import com.petcove.paymentservice.dto.PaymentRequest;
import com.petcove.paymentservice.dto.PaymentResponse;
import com.petcove.paymentservice.event.PaymentEvent;
import com.petcove.paymentservice.model.Payment;
import com.petcove.paymentservice.model.PaymentStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaymentAdapter {
    public static Payment toPayment(PaymentRequest paymentRequest){
        return Payment.builder()
                .customerId(paymentRequest.getCustomerId())
                .totalAmount(paymentRequest.getTotalAmount())
                .orderNumber(paymentRequest.getOrderNumber())
                .paymentStatus(PaymentStatus.PAID)
                .build();
    }
    public static PaymentResponse toPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .orderNumber(payment.getOrderNumber())
                .customerId(payment.getCustomerId())
                .totalAmount(payment.getTotalAmount())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }

    public static PaymentEvent toPaymentEvent(Payment payment){
        return PaymentEvent.builder()
                .orderNumber(payment.getOrderNumber())
                .customerId(payment.getCustomerId())
                .paymentStatus(payment.getPaymentStatus())
                .build();
    }
}
