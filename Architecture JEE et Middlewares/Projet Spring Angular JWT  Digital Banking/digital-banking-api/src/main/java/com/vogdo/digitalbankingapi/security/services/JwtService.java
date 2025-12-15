package com.vogdo.digitalbankingapi.security.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "votre_super_secret_pour_le_token_jwt_a_changer_absolument";
    private final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 heures
                .sign(algorithm);
    }

    // --- AJOUTER LES MÉTHODES SUIVANTES ---

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getExpiresAt();
    }

    public String extractUsername(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getSubject();
    }

    private DecodedJWT decodeToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}