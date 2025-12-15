package com.vogdo.digitalbankingapi.repositories;

import com.vogdo.digitalbankingapi.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByFirstNameContaining(String keyword);
}