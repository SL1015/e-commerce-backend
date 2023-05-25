package com.petcove.paymentservice.controller;

import com.petcove.paymentservice.dto.PaymentRequest;
import com.petcove.paymentservice.dto.PaymentResponse;
import com.petcove.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
@Slf4j
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest paymentRequest){
        log.info("Request received to pay: {}", paymentRequest);
        return new ResponseEntity<>(paymentService.createPayment(paymentRequest),  HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentResponse> getPayment(@PathVariable Long id){
        return new ResponseEntity<>(paymentService.getPayment(id),  HttpStatus.OK);
    }

    @GetMapping(value="/order/{orderNumber}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(@PathVariable("orderNumber") String orderNumber){
        return new ResponseEntity<>(paymentService.getPaymentByOrderNumber(orderNumber),  HttpStatus.OK);
    }

    @GetMapping(value="/user/{customerId}")
    public ResponseEntity<List<PaymentResponse>> getPaymentsByCustomerId(@PathVariable("customerId") Long customerId){
        log.info("get by customerid");
        return new ResponseEntity<>(paymentService.getPaymentsByCustomerId(customerId),  HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponse> updatePayment(@PathVariable Long id, @Valid @RequestBody PaymentRequest paymentRequest) {
        return new ResponseEntity<>(paymentService.updatePayment(id, paymentRequest),  HttpStatus.OK);
    }
}
