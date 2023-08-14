package com.petcove.inventoryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petcove.inventoryservice.dto.InventoryDto;
import com.petcove.inventoryservice.dto.InventoryResponse;
import com.petcove.inventoryservice.dto.ProductCreateRequest;
import com.petcove.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    //multiple items using path variable: http://localhost:8082/api/inventory/racket1,racket1-red
    // request param: http://localhost:8082/api/inventory?skuCode=racket1&skuCode=racket1-red
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    // check if the product is in stock
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode, @RequestParam List<Integer> quantity){
        return inventoryService.isInstock(skuCode,quantity);

    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<InventoryDto>> getAllProducts(){
        log.info("GET /api/inventory is called");
        return new ResponseEntity<>(inventoryService.getAllProducts(), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<InventoryDto> createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        log.info("POST /api/inventory is called");
        return new ResponseEntity<>(inventoryService.createProduct(productCreateRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{skuCode}")
    public ResponseEntity<InventoryDto> updateProduct(@PathVariable String skuCode, @Valid @RequestBody ProductCreateRequest productCreateRequest) {
        log.info("PUT /api/inventory/{} is called", skuCode);
        return new ResponseEntity<>(inventoryService.updateProduct(skuCode, productCreateRequest), HttpStatus.OK);
    }

    @GetMapping("/{skuCode}")
    public ResponseEntity<InventoryDto> getProductBySku(@PathVariable String skuCode) {
        log.info("GET /api/inventory/{} is called", skuCode);
        return new ResponseEntity<>(inventoryService.getProductBySku(skuCode), HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<InventoryDto>> filterProduct(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice) {
        log.info(category);
        return new ResponseEntity<>(inventoryService.filterProduct(category, minPrice, maxPrice), HttpStatus.OK);
    }

    @DeleteMapping("/{skuCode}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String skuCode) {
        log.info("DELETE /api/inventory/{} is called", skuCode);
        inventoryService.deleteProduct(skuCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
