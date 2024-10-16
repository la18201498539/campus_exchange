package edu.bu.cs673.secondhand.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * SecurityUtil is a utility class for handling password encryption and matching.
 * It provides methods to encode passwords and verify them against stored hashes.
 * Author: YQ
 */
//@Component
public class SecurityUtil {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();  // Password encoder instance

    /**
     * Encodes a raw password using BCrypt hashing.
     * @param password The raw password to encode.
     * @return The encoded password as a String.
     */
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);  // Encode the password
    }

    /**
     * Matches a raw password against an encoded password.
     * @param rawPassword The raw password to check.
     * @param encodedPassword The encoded password to compare against.
     * @return true if the passwords match, false otherwise.
     */
    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);  // Check if the passwords match
    }
}
