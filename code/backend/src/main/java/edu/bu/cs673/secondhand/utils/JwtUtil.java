package edu.bu.cs673.secondhand.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JwtUtil is a utility class for generating and validating JSON Web Tokens (JWT).
 * It provides methods to create tokens and extract user information from them.
 * Author: YQ
 */
//@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;  // Secret key for signing the JWT

    @Value("${jwt.expiration}")
    private Long expiration;  // Expiration time for the JWT in seconds

    /**
     * Generates a JWT token for a given user ID.
     * @param userId The ID of the user for whom the token is generated.
     * @return The generated JWT token as a String.
     */
    public String generateToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)  // Set the subject (user ID) of the token
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Set the issued date
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))  // Set the expiration date
                .signWith(SignatureAlgorithm.HS512, secret)  // Sign the token with the secret key
                .compact();  // Compact the JWT into a String
    }

    /**
     * Validates the given JWT token and retrieves the user ID from it.
     * @param token The JWT token to validate.
     * @return The user ID if the token is valid, null otherwise.
     */
    public String validateTokenAndGetUserId(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();  // Parse the token
            return claims.getSubject();  // Return the subject (user ID)
        } catch (Exception e) {
            return null;  // Return null if the token is invalid
        }
    }
}
