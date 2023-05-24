package com.petcove.paymentservice.repository;

import com.petcove.paymentservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository  extends JpaRepository<Payment,Long> {
    List<Payment> findByCustomerId(Long customerId);
    Optional<Payment> findByOrderNumber(String orderNumber);
}
