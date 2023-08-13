package com.petcove.inventoryservice.service.impl;

import com.petcove.inventoryservice.dto.InventoryDto;
import com.petcove.inventoryservice.dto.InventoryResponse;
import com.petcove.inventoryservice.dto.ProductCreateRequest;
import com.petcove.inventoryservice.exception.ProductNotFoundException;
import com.petcove.inventoryservice.model.adapter.InventoryAdapter;
import com.petcove.inventoryservice.repository.InventoryRepository;
import com.petcove.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
@EnableCaching
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
    //@SneakyThrows
    public List<InventoryResponse> isInstock(List<String> skuCode, List<Integer> quantity){
        /*Simulate time-out exception
        log.info("Wait started");
        Thread.sleep(10000);
        log.info("wait ended");
        */
        //check if the *object git is present inside the optional or not
        log.info("start isinstock");
        Map<String, Integer> sku_quan_map = new HashMap<String,Integer>();
        for(int i=0; i<skuCode.size(); i++){
            sku_quan_map.put(skuCode.get(i), quantity.get(i));
        }
        log.info(sku_quan_map.toString());
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skuCode(inventory.getSkuCode())
                                .price(inventory.getPrice())
                                .isInStock(inventory.getQuantity() >= sku_quan_map.get(inventory.getSkuCode()))
                                .build()
                ).toList();
    }

    @Override
    @Cacheable(value = "allProducts")
    public List<InventoryDto> getAllProducts() {
        doSleepTest();// test if redis caching is functioning
        return inventoryRepository.findAll().stream()
                .map(InventoryAdapter::EntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = "allProducts", allEntries = true)
    public InventoryDto createProduct(ProductCreateRequest productCreateRequest) {
        log.debug("createProduct() is called");
        log.info("Product is created" + productCreateRequest);
        return InventoryAdapter.EntityToDto(inventoryRepository.save(InventoryAdapter.RequestToEntity(productCreateRequest)));
    }

    @Override
    @CacheEvict(value = "allProducts", allEntries = true)
    @CachePut(value = "products", key = "#skuCode")
    public InventoryDto updateProduct(String skuCode, ProductCreateRequest productCreateRequest) {
        log.debug("updateProduct() is called");
        log.info("Product is updated" + productCreateRequest);
        return inventoryRepository.findBySkuCode(skuCode)
                .map(inventory -> {
                    inventory.setQuantity(productCreateRequest.getQuantity());
                    inventory.setPrice(productCreateRequest.getPrice());
                    inventory.setBrand(productCreateRequest.getBrand());
                    inventory.setColor(productCreateRequest.getColor());
                    inventory.setCategory(productCreateRequest.getCategory());
                    inventory.setDescription(productCreateRequest.getDescription());
                    return InventoryAdapter.EntityToDto(inventoryRepository.save(inventory));
                })
                .orElseThrow(ProductNotFoundException::new);
    }
    @Override
    @Cacheable(value = "products", key = "#skuCode", unless = "#result == null")
    public InventoryDto getProductBySku(String skuCode){
        doSleepTest();// test if redis caching is functioning
        return inventoryRepository.findBySkuCode(skuCode).map(InventoryAdapter::EntityToDto).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    @Cacheable(value = "filteredProducts",
            key = "#category + ':' + #minPrice + ':' + #maxPrice",
            unless = "#result == null")
    public List<InventoryDto> filterProduct(String category, BigDecimal minPrice, BigDecimal maxPrice){
        doSleepTest(); // test if redis caching is functioning
        return inventoryRepository.filter(category, minPrice, maxPrice).stream()
                .map(InventoryAdapter::EntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "allProducts", allEntries = true),
                    @CacheEvict(value = "products", key = "#skuCode")
            }
    )
    public void deleteProduct(String skuCode) {
        log.debug("deleteProduct() is called");
        log.info("Product is deleted" + skuCode);
        inventoryRepository.deleteBySkuCode(skuCode);
    }

    @Override
    public void doSleepTest() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
