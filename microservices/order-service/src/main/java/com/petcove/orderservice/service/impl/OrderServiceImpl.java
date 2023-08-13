package com.petcove.orderservice.service.impl;

import com.petcove.orderservice.dto.InventoryResponse;
import com.petcove.orderservice.dto.OrderDto;
import com.petcove.orderservice.dto.OrderLineItemsDto;
import com.petcove.orderservice.dto.OrderRequest;
import com.petcove.orderservice.event.OrderPlacedEvent;
import com.petcove.orderservice.model.Order;
import com.petcove.orderservice.model.OrderLineItems;
import com.petcove.orderservice.model.OrderStatus;
import com.petcove.orderservice.model.adapter.OrderAdapter;
import com.petcove.orderservice.repository.OrderRepository;
import com.petcove.orderservice.service.OrderService;
import com.petcove.orderservice.exception.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@EnableCaching
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    private BigDecimal totalAmount;

    @Override
    @Cacheable(value = "allOrders")
    public List<OrderDto> getAllOrders() {
        doSleepTest();// test if redis caching is functioning
        return orderRepository.findAll().stream()
                .map(OrderAdapter::toOrderDto)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "allOrders", allEntries = true)
    public String placeOrder(OrderRequest orderRequest){

        Order order = OrderAdapter.toOrderEntity(orderRequest);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();
        List<Integer> quantities = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getQuantity)
                .toList();

        //final ParameterizedTypeReference<Map<String, Integer>> MAP_TYPE_REF = new ParameterizedTypeReference<>() {};

        // Call Inventory service and place order if the product is in stock;
        InventoryResponse[] inventoryResponseArr = webClientBuilder.build().get() //synchronous request
                //build the uri with the query params as skuCodes
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes)
                                .queryParam("quantity",quantities)
                                .build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class) //read the response
                .block();
        boolean allProductsInStock = Arrays.stream(inventoryResponseArr)
                .allMatch(InventoryResponse::isInStock);
        log.info(Arrays.toString(inventoryResponseArr));
        log.info(String.valueOf(allProductsInStock));


        if(allProductsInStock){
            order.setOrderStatus(OrderStatus.PLACED);
            List<OrderLineItems> orderItems = order.getOrderLineItemsList();
            totalAmount = order.getTotalAmount();
            log.info(totalAmount.toString());
            for(InventoryResponse inventoryResponse:inventoryResponseArr){
                for(OrderLineItems orderItem:orderItems){
                    if(Objects.equals(orderItem.getSkuCode(), inventoryResponse.getSkuCode())){
                        orderItem.setPrice(inventoryResponse.getPrice());
                        BigDecimal itemAmount = orderItem.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity()));
                        this.totalAmount =totalAmount.add(itemAmount);
                        log.info(totalAmount.toString());
                    }
                }
            }
            log.info(totalAmount.toString());
            order.setTotalAmount(totalAmount);
            log.info(order.getOrderLineItemsList().toString());
            orderRepository.save(order);

            // send the order placed event as a msg to the notification topic
            //kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
            kafkaTemplate.send("orderplacedEvent", new OrderPlacedEvent(order.getOrderNumber()
                    , order.getCustomerId()
                    , order.getTotalAmount()
                    , OrderAdapter.toOrderItemEventList(order.getOrderLineItemsList())));
            //log.debug();
            return "Order placed successfully.";
        }else {
            log.info("else");
            throw new IllegalArgumentException("Product is not in stock at the moment.");
        }
    }
    /*
    @Override
    public OrderDto updateOrder(String orderNumber, OrderRequest orderRequest){
        return orderRepository.findByOrderNumber(orderNumber).map(order -> {
            order.setOrderLineItemsList(OrderAdapter.toOrderItemEntityList(orderRequest.getOrderLineItemsDtoList(), order));
            return OrderAdapter.toOrderDto(orderRepository.save(order));
        }).orElseThrow(OrderNotFoundException::new);
    }
    */
    @Override
    @Cacheable(value = "orders", key = "#orderNumber", unless = "#result == null")
    public OrderDto getOrder(String orderNumber){
        doSleepTest();
        log.info(orderNumber);
        log.info(String.valueOf(orderRepository.findByOrderNumber(orderNumber).isEmpty()));
        return orderRepository.findByOrderNumber(orderNumber).map(OrderAdapter::toOrderDto).orElseThrow(OrderNotFoundException::new);
    }
    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "allOrders", allEntries = true),
                    @CacheEvict(value = "orders", key = "#orderNumber")
            }
    )
    public void cancelOrder(String orderNumber) {
        log.info("Order {} is canceled", orderNumber);
        orderRepository.deleteByOrderNumber(orderNumber);
    }
    @Override
    public void doSleepTest() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
