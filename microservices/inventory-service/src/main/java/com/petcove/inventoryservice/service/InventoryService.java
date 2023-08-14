package com.petcove.inventoryservice.service;

import com.petcove.inventoryservice.dto.InventoryResponse;
import com.petcove.inventoryservice.dto.ProductCreateRequest;
import com.petcove.inventoryservice.dto.InventoryDto;

import java.math.BigDecimal;
import java.util.List;


public interface InventoryService {
    List<InventoryResponse> isInstock(List<String> skuCode, List<Integer> quantity);
    List<InventoryDto> getAllProducts();
    InventoryDto createProduct(ProductCreateRequest productCreateRequest);
    InventoryDto updateProduct(String skuCode, ProductCreateRequest productCreateRequest);
    InventoryDto getProductBySku(String skuCode);
    List<InventoryDto> filterProduct(String category, BigDecimal minPrice, BigDecimal maxPrice);
    void deleteProduct(String skuCode);
    void doSleepTest();
}

