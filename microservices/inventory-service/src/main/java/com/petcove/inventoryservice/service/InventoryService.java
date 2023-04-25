package com.petcove.inventoryservice.service;

import com.petcove.inventoryservice.dto.InventoryResponse;
import com.petcove.inventoryservice.dto.ProductAddRequest;
import com.petcove.inventoryservice.dto.InventoryDto;
import com.petcove.inventoryservice.model.Inventory;
import com.petcove.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;


public interface InventoryService {
    List<InventoryResponse> isInstock(List<String> skuCode, List<Integer> quantity);
    InventoryDto createProduct(ProductAddRequest productAddRequest);
    InventoryDto updateProduct(String skuCode, ProductAddRequest productAddRequest);

    void deleteProduct(String skuCode);
}

