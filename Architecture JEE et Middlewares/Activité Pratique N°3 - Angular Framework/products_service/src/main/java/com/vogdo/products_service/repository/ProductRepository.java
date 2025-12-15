package com.vogdo.products_service.repository;

import com.vogdo.products_service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
