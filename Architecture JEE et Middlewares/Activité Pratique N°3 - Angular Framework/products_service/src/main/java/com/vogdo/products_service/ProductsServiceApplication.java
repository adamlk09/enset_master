package com.vogdo.products_service;

import com.vogdo.products_service.entities.Product;
import com.vogdo.products_service.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository){
        return args -> {
            Product product = Product.builder()
                    .name("Computer").price(5400).quantity(11)
                    .build();
            productRepository.save(product);
            productRepository.save(Product.builder().name("Printer").price(1299).quantity(11).build());
            productRepository.save(Product.builder().name("Smart Phone").price(12000).quantity(33).build());
            productRepository.findAll().forEach(p->{
                System.out.println(p.toString());
            });
        } ;
    }

}
