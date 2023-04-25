package com.petcove.orderservice.model.adapter;

import com.petcove.orderservice.dto.OrderLineItemsDto;
import com.petcove.orderservice.dto.OrderRequest;
import com.petcove.orderservice.event.OrderItemEvent;
import com.petcove.orderservice.model.Order;
import com.petcove.orderservice.model.OrderLineItems;
import com.petcove.orderservice.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Slf4j
public class OrderAdapter {

    // Request to order entity
    public static Order toOrderEntity(OrderRequest orderRequest) {
        Order order = Order.builder()
                .customerId(orderRequest.getCustomerId())
                .totalAmount(orderRequest.getTotalAmount())
                //.orderDate(orderAddRequest.getOrderDate())
                .orderNumber(UUID.randomUUID().toString())
                .orderStatus(OrderStatus.PENDING)
                .build();
        log.info("order build 1");
        order.setOrderLineItemsList(toOrderItemEntityList(orderRequest.getOrderLineItemsDtoList(), order));
        log.info("order build 2");
        return order;
    }
    public static List<OrderLineItems> toOrderItemEntityList(List<OrderLineItemsDto> orderItemAddRequests, Order order) {
        return orderItemAddRequests.stream().map(orderentity -> toOrderItemEntity(orderentity, order)).collect(Collectors.toList());
    }
    public static OrderLineItems toOrderItemEntity(OrderLineItemsDto orderLineItemsDto, Order order) {
        return OrderLineItems.builder()
                .id(orderLineItemsDto.getId())
                .skuCode(orderLineItemsDto.getSkuCode())
                .quantity(orderLineItemsDto.getQuantity())
                .price(orderLineItemsDto.getPrice())
                .order(order)
                .build();
    }

    //order line items to event list
    public static List<OrderItemEvent> toOrderItemEventList(List<OrderLineItems> orderItems) {
        return orderItems.stream()
                .map(OrderAdapter::toOrderItemEvent)
                .collect(Collectors.toList());
    }
    //item entity to event
    public static OrderItemEvent toOrderItemEvent(OrderLineItems orderItems) {
        return OrderItemEvent.builder()
                .id(orderItems.getId())
                .skuCode(orderItems.getSkuCode())
                .quantity(orderItems.getQuantity())
                .price(orderItems.getPrice())
                .build();
    }
    //Dto to entity
    public OrderLineItems DtoToOrderItems(OrderLineItemsDto orderLineItemsDto){
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
