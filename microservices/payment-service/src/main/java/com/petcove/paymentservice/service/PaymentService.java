package com.petcove.paymentservice.service;

import com.petcove.paymentservice.dto.PaymentRequest;
import com.petcove.paymentservice.dto.PaymentResponse;

import java.util.List;

public interface PaymentService {
    PaymentResponse createPayment(PaymentRequest paymentRequest);

    //PaymentResponse getPayment(Long id);

    //PaymentResponse updatePayment(Long id, PaymentRequest paymentRequest);

    //List<PaymentResponse> getPaymentsByCustomerId(Long id);
}
