package com.petcove.paymentservice.service.impl;

import com.petcove.paymentservice.dto.PaymentRequest;
import com.petcove.paymentservice.dto.PaymentResponse;
import com.petcove.paymentservice.event.PaymentEvent;
import com.petcove.paymentservice.model.Payment;
import com.petcove.paymentservice.model.adapter.PaymentAdapter;
import com.petcove.paymentservice.repository.PaymentRepository;
import com.petcove.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.petcove.paymentservice.model.adapter.PaymentAdapter.toPayment;
import static com.petcove.paymentservice.model.adapter.PaymentAdapter.toPaymentResponse;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    public PaymentResponse createPayment(PaymentRequest paymentRequest){
        Payment payment = PaymentAdapter.toPayment(paymentRequest);
        PaymentEvent paymentEvent = PaymentAdapter.toPaymentEvent(payment);
        paymentRepository.save(payment);
        kafkaTemplate.send("paymentEvent",paymentEvent);
        return PaymentAdapter.toPaymentResponse(payment);
    }
}
