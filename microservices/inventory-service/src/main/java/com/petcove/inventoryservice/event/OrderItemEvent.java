package com.petcove.inventoryservice.event;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemEvent implements Serializable  {
    private Long id;

    private String skuCode;

    private Integer quantity;

    private BigDecimal price;

}