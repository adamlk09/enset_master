package com.vogdo.digitalbankingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vogdo.digitalbankingapi.entities.Customer;
import com.vogdo.digitalbankingapi.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApiApplication.class, args);
    }

    // Au démarrage, ajoute quelques clients pour les tests
    @Bean
    CommandLineRunner start(CustomerRepository customerRepository){
        return args -> {
            Stream.of("Hassan", "Imane", "Mohamed").forEach(name -> {
                Customer customer = new Customer();
                customer.setFirstName(name);
                customer.setLastName("Client");
                customer.setEmail(name.toLowerCase() + "@gmail.com");
                customerRepository.save(customer);
            });
        };
    }

    /*@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }*/

    /*@Bean
    CommandLineRunner hashPassword(PasswordEncoder passwordEncoder) {
        return args -> {
            String hashedPassword = passwordEncoder.encode("password");
            System.out.println("******************************************");
            System.out.println("HASHED PASSWORD FOR 'password' IS: " + hashedPassword);
            System.out.println("******************************************");
        };
    }*/

}