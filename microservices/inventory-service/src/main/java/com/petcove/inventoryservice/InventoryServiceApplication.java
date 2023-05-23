package com.petcove.inventoryservice;

import com.petcove.inventoryservice.model.Inventory;
import com.petcove.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.swing.*;
import java.math.BigDecimal;

@SpringBootApplication
@EnableJpaRepositories
public class InventoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("racket");
			inventory.setQuantity(155);
			inventory.setCategory("Badminton");
			inventory.setBrand("Yonex");
			inventory.setPrice(BigDecimal.valueOf(199));
			inventory.setDescription("A balanced badminton racket.");
			inventory.setColor("Purple");

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("racket1_red");
			inventory1.setQuantity(166);
			inventory1.setCategory("Badminton");
			inventory1.setBrand("Yonex");
			inventory1.setPrice(BigDecimal.valueOf(235));
			inventory1.setDescription("A Head-heavy racket that improves smash performance.");
			inventory1.setColor("Red");

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}

}
