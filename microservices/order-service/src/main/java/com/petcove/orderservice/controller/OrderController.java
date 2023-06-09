package com.petcove.orderservice.controller;

import com.petcove.orderservice.dto.OrderDto;
import com.petcove.orderservice.dto.OrderRequest;
import com.petcove.orderservice.model.OrderStatus;
import com.petcove.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@Validated
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //@CircuitBreaker(name="inventory", fallbackMethod = "fallbackMethod")
    //@TimeLimiter(name="inventory")
    //@Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        log.info("/api/order is called with POST method");
        return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
    }

    // multiple fallback method can be defined here, the exception type should be the differentiator
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, RuntimeException runtimeException){

        return CompletableFuture.supplyAsync(()->"Something went wrong. Please try it later.");
    }
    public CompletableFuture<String> fallbackMethod(OrderRequest orderRequest, IllegalArgumentException illegalArgumentException){

        return CompletableFuture.supplyAsync(()->"Product is not in stock at the moment.");
    }
    /*
    @PutMapping({"/", "/{orderNumber}"})
    public ResponseEntity<OrderDto> updateOrder(@PathVariable("orderNumber") String orderNumber, @Valid @RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(orderService.updateOrder(orderNumber,orderRequest), HttpStatus.OK);
    }*/

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("orderNumber") String orderNumber) {
        log.info("Request received to get order with id: {}", orderNumber);
        return new ResponseEntity<>(orderService.getOrder(orderNumber),  HttpStatus.OK);
    }
    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<OrderDto> cancelOrder(@PathVariable String orderNumber) {
        orderService.cancelOrder(orderNumber);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
