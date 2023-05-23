package com.petcove.orderservice.event;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;


@Builder
@Getter
@Setter
public class OrderItemEvent implements Serializable {
    private String orderNumber;

    private String skuCode;

    private Integer quantity;

    private BigDecimal price;

}