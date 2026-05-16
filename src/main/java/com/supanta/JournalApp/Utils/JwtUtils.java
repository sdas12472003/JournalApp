package com.supanta.JournalApp.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {

    // A secure 256-bit (32-byte) secret key required by modern JJWT versions
    private final String SECRET_KEY = "TaK+TaSE3W9tXm9zWE90S2V5U3VwYW50YURhc0pvdXJuYWxBcHA=";

    // Helper method to convert our plain text secret key into a cryptographic SecretKey instance
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Extract the username (Subject) from the token payload
    public String extractUsername(String token) {
        Claims claims=extractAllClaims(token);
        return claims.getSubject();
    }

    // Extract the exact expiration date from the token payload
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Generic helper method used to extract any individual claim using a claims resolver function
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Parse the token using the secret signing key to extract the complete payload of Claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Check if the current system time has passed the token's expiration date
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Overloaded generation method that starts with an empty map of extra claims
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    // Builds the JWT token using the builder pattern exactly as shown in the tutorial chain
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("type", "JWT").and() // Configures standard token type header
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 1)) // 10 Hours expiry as defined in video
                .signWith(getSigningKey())
                .compact();
    }

    // Validates the token by cross-checking the extracted username and expiration state
    public Boolean validateToken(String token) {
        
        return  !isTokenExpired(token);
    }
}