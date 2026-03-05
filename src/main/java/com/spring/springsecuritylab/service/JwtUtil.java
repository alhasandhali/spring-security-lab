package com.spring.springsecuritylab.service;

public interface JwtUtil {
    String generateToken(String email);

    String extractEmail(String token);
}
