package com.example.dvomed.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;

import com.example.dvomed.entities.Role;

import javax.crypto.SecretKey;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {

    //dynamic secret key
    // private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret.key}") String secretKeyProperty) {
        if (secretKeyProperty == null || secretKeyProperty.isEmpty()) {
            throw new IllegalStateException("JWT secret key is not configured.");
        }
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyProperty);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // public String generateToken(String username, Role role) {
    //     Map<String, Object> claims = new HashMap<>();
    //     claims.put("role", role);
    //     return Jwts.builder()
    //             .setClaims(claims)
    //             .setSubject(username)
    //             .setIssuedAt(new Date())
    //             .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 10 hours
    //             .signWith(secretKey)
    //             .compact();
    // }
    public String generateToken(Authentication authentication, String role) {
        String username = authentication.getName();
        return Jwts.builder()
                .setSubject(username)
                // .claim("role", role)
                .claim("role", "ROLE_" + role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 24 hours
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String extractRole(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return  (String) claims.get("role");
    }

    //dvo added now
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}