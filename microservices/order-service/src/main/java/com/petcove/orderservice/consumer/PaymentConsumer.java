package com.petcove.orderservice.consumer;

import com.petcove.orderservice.dto.OrderDto;
import com.petcove.orderservice.event.PaymentEvent;
import com.petcove.orderservice.model.Order;
import com.petcove.orderservice.model.OrderStatus;
import com.petcove.orderservice.repository.OrderRepository;
import com.petcove.orderservice.model.adapter.OrderAdapter;
import com.petcove.orderservice.exception.OrderNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Component
public class PaymentConsumer {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    @KafkaListener(topics = "paymentEvent", groupId = "paymentID")
    public Optional<Order> consume(PaymentEvent paymentEvent) {
        //fetch the payment status from paymentevent, then send an update order request.
        log.info("received payment event");
        log.info(paymentEvent.getOrderNumber());
        return orderRepository.findByOrderNumber(paymentEvent.getOrderNumber())
                .map(order -> {
                    order.setOrderStatus(OrderStatus.PAID);
                    return order;
                })
                .map(orderRepository::save);
    }
}
