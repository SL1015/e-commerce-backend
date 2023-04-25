package com.petcove.inventoryservice.consumer;


import com.petcove.inventoryservice.event.OrderPlacedEvent;
import com.petcove.inventoryservice.repository.InventoryRepository;
import com.petcove.inventoryservice.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderPlacedConsumer {

    private final InventoryRepository inventoryRepository;
    @KafkaListener(topics = "orderplacedEvent")
    public void consume(OrderPlacedEvent orderPlacedEvent) {
        log.info("OrderPlacedEvent consumed: {}", orderPlacedEvent);
        var orderItems = orderPlacedEvent.getOrderItems();
        orderItems.forEach(orderItem -> {
            log.info("OrderItem consumed: {}", orderItem);

            var item = inventoryRepository.findBySkuCode(orderItem.getSkuCode()).orElseThrow(ProductNotFoundException::new);
            item.setQuantity(item.getQuantity() - orderItem.getQuantity());
            inventoryRepository.save(item);
        });
        log.info("OrderPlacedEvent consumed successfully: {}", orderPlacedEvent);
    }

}