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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
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
    public InventoryDto createProduct(ProductCreateRequest productCreateRequest) {
        log.debug("createProduct() is called");
        log.info("Product is created" + productCreateRequest);
        return InventoryAdapter.EntityToDto(inventoryRepository.save(InventoryAdapter.RequestToEntity(productCreateRequest)));
    }

    @Override
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
    public InventoryDto getProduct(String skuCode){
        return inventoryRepository.findBySkuCode(skuCode).map(InventoryAdapter::EntityToDto).orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public void deleteProduct(String skuCode) {
        log.debug("deleteProduct() is called");
        log.info("Product is deleted" + skuCode);
        inventoryRepository.deleteBySkuCode(skuCode);
    }
}
