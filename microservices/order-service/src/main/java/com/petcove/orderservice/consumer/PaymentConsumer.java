package com.petcove.orderservice.consumer;

import com.petcove.orderservice.event.PaymentEvent;
import com.petcove.orderservice.model.Order;
import com.petcove.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentConsumer {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;
    @KafkaListener(topics = "paymentEvent", groupId = "paymentID")
    public void consume(PaymentEvent paymentEvent) {
        //fetch the payment status from paymentevent, then send an update order request.
        Optional<Order> order = orderRepository.findByOrderNumber(paymentEvent.getOrderNumber());
        log.info(order.toString());

    }
}
