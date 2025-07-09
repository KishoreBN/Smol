package com.primeengineer.smol.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;


/**
 * Utility class for generating and validating JWT tokens.
 * <p>
 * This class provides methods to:
 * - Generate a JWT for a given user
 * - Validate the integrity and expiration of a token
 * - Extract username from a token
 * </p>
 * Algorithm used for signature - HMAC with SHA encryption
 */
@Component
public class JwtUtil {
    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.expiration}")
    private Long expiration;

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuer("smolapp")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() * expiration))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSecretKey())
                    .build()
                    .parseSignedClaims(jwtToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String jwtToken) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSecretKey())
                .build()
                .parseSignedClaims(jwtToken)
                .getPayload()
                .getSubject();
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
