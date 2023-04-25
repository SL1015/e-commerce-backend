package com.petcove.inventoryservice.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException() {
        super("Product not found in the inventory.");
    }
}
