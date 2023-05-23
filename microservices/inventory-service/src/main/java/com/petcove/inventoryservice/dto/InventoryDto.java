package com.petcove.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto implements Serializable {

    private Integer quantity;
    private String skuCode;
    private String description;
    private BigDecimal price;
    private String category;
    private String color;
    private String brand;
}