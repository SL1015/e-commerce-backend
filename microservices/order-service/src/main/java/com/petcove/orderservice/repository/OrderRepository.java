package com.petcove.orderservice.repository;

import com.petcove.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String OrderNumber);

    void deleteByOrderNumber(String orderNumber);
}
