package com.petcove.inventoryservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Table(name= "t_inventory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inventory {
    @Id
    private String skuCode;
    private Integer quantity;
    private String description;
    private BigDecimal price;
    private String category;
    private String color;
    private String brand;
}
