package com.petcove.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateRequest {
    private String skuCode;
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private String category;
    private String color;
    private String brand;
}