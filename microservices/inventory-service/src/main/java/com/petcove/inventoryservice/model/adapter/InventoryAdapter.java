package com.petcove.inventoryservice.model.adapter;

import com.petcove.inventoryservice.dto.InventoryDto;
import com.petcove.inventoryservice.dto.ProductCreateRequest;
import com.petcove.inventoryservice.model.Inventory;

public class InventoryAdapter {
    public static Inventory RequestToEntity(ProductCreateRequest productCreateRequest){
        return Inventory.builder()
                .skuCode(productCreateRequest.getSkuCode())
                .quantity(productCreateRequest.getQuantity())
                .brand(productCreateRequest.getBrand())
                .category(productCreateRequest.getCategory())
                .color(productCreateRequest.getColor())
                .description(productCreateRequest.getDescription())
                .price(productCreateRequest.getPrice())
                .build();
    }

    public static InventoryDto EntityToDto(Inventory inventory) {
        return InventoryDto.builder()
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .description(inventory.getDescription())
                .color(inventory.getColor())
                .brand(inventory.getBrand())
                .category(inventory.getCategory())
                .price(inventory.getPrice())
                .build();
    }
}
