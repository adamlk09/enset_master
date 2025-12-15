package com.vogdo.digitalbankingapi.security.services;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // NOTE: Ici, vous devriez charger l'utilisateur depuis votre base de données (AppUser).
    // Pour cet exemple, nous utilisons un utilisateur en mémoire.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user".equals(username)) {
            // Dans un vrai projet, le mot de passe serait hashé (ex: avec BCryptPasswordEncoder)
            //return new User("user", "$2a$10$...", new ArrayList<>()); // Remplacez par un mdp hashé
            return new User("user", "$2a$10$1/xA5aSW4hyvm365qRxgjOrTRDW7YZKcOioyudEX3Twap0cOw6N4K", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}