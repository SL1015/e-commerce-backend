package com.petcove.paymentservice.service;

import com.petcove.paymentservice.dto.PaymentRequest;
import com.petcove.paymentservice.dto.PaymentResponse;

import java.util.List;

public interface PaymentService {
    List<PaymentResponse> getAllPayments();
    PaymentResponse createPayment(PaymentRequest paymentRequest);
    PaymentResponse getPayment(Long id);
    PaymentResponse getPaymentByOrderNumber(String orderNumber);
    List<PaymentResponse> getPaymentsByCustomerId(Long customerId);
    PaymentResponse updatePayment(Long id, PaymentRequest paymentRequest);
    void doSleepTest();
}
