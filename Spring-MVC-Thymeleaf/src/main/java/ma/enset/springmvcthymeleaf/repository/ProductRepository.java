package ma.enset.springmvcthymeleaf.repository;

import ma.enset.springmvcthymeleaf.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

}