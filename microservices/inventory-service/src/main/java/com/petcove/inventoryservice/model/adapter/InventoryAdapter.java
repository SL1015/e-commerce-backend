package com.petcove.inventoryservice.model.adapter;

import com.petcove.inventoryservice.dto.InventoryDto;
import com.petcove.inventoryservice.dto.ProductAddRequest;
import com.petcove.inventoryservice.model.Inventory;

public class InventoryAdapter {
    public static Inventory RequestToEntity(ProductAddRequest productAddRequest){
        return Inventory.builder()
                .skuCode(productAddRequest.getSkuCode())
                .quantity(productAddRequest.getQuantity())
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
