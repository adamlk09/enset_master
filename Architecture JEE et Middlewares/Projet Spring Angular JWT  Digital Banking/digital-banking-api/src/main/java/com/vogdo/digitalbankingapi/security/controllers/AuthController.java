package com.vogdo.digitalbankingapi.security.controllers;

import com.vogdo.digitalbankingapi.security.dtos.LoginRequest;
import com.vogdo.digitalbankingapi.security.dtos.TokenResponse;
import com.vogdo.digitalbankingapi.security.services.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest loginRequest) {
        // Authentifier l'utilisateur avec le manager de Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        // Si l'authentification réussit, récupérer les détails de l'utilisateur
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Générer le token JWT
        String token = jwtService.generateToken(userDetails);

        // Renvoyer le token dans la réponse
        return new TokenResponse(token);
    }
}