package com.ajru.pharmacy_product_system;

import com.ajru.pharmacy_product_system.model.Product;
import com.ajru.pharmacy_product_system.reposiroty.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PharmacyProductSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(PharmacyProductSystemApplication.class, args);
	}

}
