package com.petcove.inventoryservice.repository;

import com.petcove.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.w3c.dom.Entity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,String> {

    List<Inventory> findBySkuCodeIn(List<String> skuCode);
    Optional<Inventory> findBySkuCode(String skuCode);

    @Query(value="select i from Inventory i where"+
            " i.skuCode = :sku AND i.quantity >= :quan")
    List<Inventory> checkAvailability(@Param("sku") String skuCode, @Param("quan") Integer quantity);

    @Query(value = "SELECT i FROM Inventory i WHERE " +
            "(:category IS NULL OR i.category = :category) AND " +
            "(:minPrice IS NULL OR i.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR i.price <= :maxPrice)")
    List<Inventory> filter(@Param("category") String category, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);

    List<Inventory> deleteBySkuCode(String skuCode);

}
