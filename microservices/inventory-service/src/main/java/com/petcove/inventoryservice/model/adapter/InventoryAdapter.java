package com.petcove.inventoryservice.model.adapter;

import com.petcove.inventoryservice.dto.InventoryDto;
import com.petcove.inventoryservice.dto.ProductCreateRequest;
import com.petcove.inventoryservice.model.Inventory;

public class InventoryAdapter {
    public static Inventory RequestToEntity(ProductCreateRequest productCreateRequest){
        return Inventory.builder()
                .skuCode(productCreateRequest.getSkuCode())
                .quantity(productCreateRequest.getQuantity())
                .build();
    }

    public static InventoryDto EntityToDto(Inventory inventory) {
        return InventoryDto.builder()
                .id(inventory.getId())
                .skuCode(inventory.getSkuCode())
                .quantity(inventory.getQuantity())
                .build();
    }
}
