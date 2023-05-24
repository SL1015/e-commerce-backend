package com.petcove.inventoryservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "SkuCode is required")
    private String skuCode;
    @NotNull(message = "Quantity is required.")
    private Integer quantity;
    @NotBlank(message = "Description is required")
    private String description;
    @NotNull(message = "Price is required.")
    private BigDecimal price;
    @NotBlank(message = "Category is required")
    private String category;
    @NotBlank(message = "Color is required")
    private String color;
    @NotBlank(message = "Brand is required")
    private String brand;
}