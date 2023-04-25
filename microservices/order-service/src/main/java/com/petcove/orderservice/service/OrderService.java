package com.petcove.orderservice.service;

import com.petcove.orderservice.dto.InventoryResponse;
import com.petcove.orderservice.dto.OrderLineItemsDto;
import com.petcove.orderservice.dto.OrderRequest;
import com.petcove.orderservice.event.OrderPlacedEvent;
import com.petcove.orderservice.model.Order;
import com.petcove.orderservice.model.OrderLineItems;
import com.petcove.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;


public interface OrderService {
    String placeOrder(OrderRequest orderRequest);

    OrderLineItems DtoToOrderItems(OrderLineItemsDto orderLineItemsDto);
    //OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto);
}
