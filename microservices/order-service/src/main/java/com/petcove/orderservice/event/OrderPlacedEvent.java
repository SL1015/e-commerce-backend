package com.petcove.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPlacedEvent implements Serializable {

    private String orderNumber;
    private Long customerId;
    private BigDecimal totalAmount;
    private List<OrderItemEvent> orderItems;
}
