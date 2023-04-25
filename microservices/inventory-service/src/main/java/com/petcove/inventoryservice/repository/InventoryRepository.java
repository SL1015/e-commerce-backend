package com.petcove.inventoryservice.repository;

import com.petcove.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.Entity;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long> {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
    Optional<Inventory> findBySkuCode(String skuCode);

    @Query(value="select i from Inventory i where"+
            " i.skuCode = :sku AND i.quantity >= :quan")
    List<Inventory> checkAvailability(@Param("sku") String skuCode, @Param("quan") Integer quantity);

    List<Inventory> deleteBySkuCode(String skuCode);

    //List<Inventory> findBySkuCodeAndQuantityGreaterThanEqual(List<String> skuCode, List<Integer> quantity);
}
