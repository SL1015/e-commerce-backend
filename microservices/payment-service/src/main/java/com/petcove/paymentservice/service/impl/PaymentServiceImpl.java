package com.petcove.paymentservice.service.impl;

import com.petcove.paymentservice.dto.PaymentRequest;
import com.petcove.paymentservice.dto.PaymentResponse;
import com.petcove.paymentservice.event.PaymentEvent;
import com.petcove.paymentservice.model.Payment;
import com.petcove.paymentservice.model.adapter.PaymentAdapter;
import com.petcove.paymentservice.repository.PaymentRepository;
import com.petcove.paymentservice.service.PaymentService;
import com.petcove.paymentservice.exception.PaymentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.petcove.paymentservice.model.adapter.PaymentAdapter.toPayment;
import static com.petcove.paymentservice.model.adapter.PaymentAdapter.toPaymentResponse;

@Service
@RequiredArgsConstructor
//@Transactional
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    @Override
    public PaymentResponse createPayment(PaymentRequest paymentRequest){
        Payment payment = PaymentAdapter.toPayment(paymentRequest);
        PaymentEvent paymentEvent = PaymentAdapter.toPaymentEvent(payment);
        paymentRepository.save(payment);
        log.info(payment.getPaymentStatus().toString());
        kafkaTemplate.send("paymentEvent",paymentEvent);
        return PaymentAdapter.toPaymentResponse(payment);
    }
    @Override
    public PaymentResponse getPayment(Long id){
        return PaymentAdapter.toPaymentResponse(paymentRepository.findById(id).orElseThrow(PaymentNotFoundException::new));
    }
    @Override
    public PaymentResponse getPaymentByOrderNumber(String orderNumber){
        return PaymentAdapter.toPaymentResponse(paymentRepository.findByOrderNumber(orderNumber).orElseThrow(PaymentNotFoundException::new));
    }
    @Override
    public List<PaymentResponse> getPaymentsByCustomerId(Long customerId){
        log.info(String.valueOf(customerId));
        return paymentRepository.findByCustomerId(customerId).stream().map(PaymentAdapter::toPaymentResponse).collect(Collectors.toList());
    }
    @Override
    public PaymentResponse updatePayment(Long id, PaymentRequest paymentRequest){
        return PaymentAdapter.toPaymentResponse(paymentRepository.findById(id).map(payment -> {
            payment.setOrderNumber(paymentRequest.getOrderNumber());
            payment.setCustomerId(paymentRequest.getCustomerId());
            payment.setTotalAmount(paymentRequest.getTotalAmount());
            return paymentRepository.save(payment);
        }).orElseThrow(PaymentNotFoundException::new));
    }
}
