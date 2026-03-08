package com.spring.springsecuritylab.service;

import com.spring.springsecuritylab.entity.Role;

import java.util.Date;

public interface JwtUtil {
    String generateToken(String email, Role role);

    String extractEmail(String token);

    Date extractExpiration(String token);

    boolean isTokenNotExpired(String token);

    boolean validateToken(String token, String email);

}
