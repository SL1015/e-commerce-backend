package com.petcove.orderservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {

    private Long id;

    //@NotNull(message = "SkuCode cannot be null")
    //@JsonProperty("skuCode")
    private String skuCode;

    //@Min(value = 1, message = "Price must be greater than or equal to 1")
    //@NotNull
    private BigDecimal price;

    //@NotNull
    //@Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private Integer quantity;
}
