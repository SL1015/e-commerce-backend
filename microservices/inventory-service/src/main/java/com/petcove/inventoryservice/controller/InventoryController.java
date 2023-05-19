package com.petcove.inventoryservice.controller;

import com.petcove.inventoryservice.dto.InventoryDto;
import com.petcove.inventoryservice.dto.InventoryResponse;
import com.petcove.inventoryservice.dto.ProductAddRequest;
import com.petcove.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;
    //multiple items (path variable): http://localhost:8082/api/inventory/racket1,racket1-red
    // request param: http://localhost:8082/api/inventory?skuCode=racket1&skuCode=racket1-red
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    //判断product是否in stock
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode, @RequestParam List<Integer> quantity){
        return inventoryService.isInstock(skuCode,quantity);

    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InventoryDto> createProduct(@RequestBody ProductAddRequest productAddRequest) {
        log.info("POST /api/inventory is called");
        return new ResponseEntity<>(inventoryService.createProduct(productAddRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<InventoryDto> updateProduct(@PathVariable String skuCode, @RequestBody ProductAddRequest productAddRequest) {
        log.info("PUT /api/inventory/{} is called", skuCode);
        return new ResponseEntity<>(inventoryService.updateProduct(skuCode, productAddRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Void> deleteProduct(@PathVariable String skuCode) {
        log.info("DELETE /api/inventory/{} is called", skuCode);
        inventoryService.deleteProduct(skuCode);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
