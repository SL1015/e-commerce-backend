package com.petcove.inventoryservice.service;

import com.petcove.inventoryservice.dto.InventoryResponse;
import com.petcove.inventoryservice.dto.ProductCreateRequest;
import com.petcove.inventoryservice.dto.InventoryDto;

import java.util.List;


public interface InventoryService {
    List<InventoryResponse> isInstock(List<String> skuCode, List<Integer> quantity);
    InventoryDto createProduct(ProductCreateRequest productCreateRequest);
    InventoryDto updateProduct(String skuCode, ProductCreateRequest productCreateRequest);

    void deleteProduct(String skuCode);
}

