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
			inventory.setQuantity(15);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("racket1_red");
			inventory1.setQuantity(4);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}

}
