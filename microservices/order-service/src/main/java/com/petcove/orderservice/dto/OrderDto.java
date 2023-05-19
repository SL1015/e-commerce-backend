package com.petcove.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.petcove.orderservice.model.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Builder
@Data
public class OrderDto implements Serializable {
    @JsonProperty("order_number")
    private String orderNumber;

    @JsonProperty("customer_id")
    private Long customerId;

    //@DecimalMin(value = "0.0", message = "Total amount must be greater than or equal to 0")
    @JsonProperty("total_amount")
    private BigDecimal totalAmount;
/*
    //@NotNull(message = "Order date cannot be null")
    @JsonProperty("order_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate;
    */
    @JsonProperty("order_status")
    private OrderStatus orderStatus;

    //@NotEmpty(message = "Order Items cannot be empty")
    //@Valid
    @JsonProperty("order_items")
    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
