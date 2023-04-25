package com.petcove.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto implements Serializable {

    private Long id;

    private Integer quantity;

    private String skuCode;
}