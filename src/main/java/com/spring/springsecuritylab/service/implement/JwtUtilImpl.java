package com.spring.springsecuritylab.service.implement;

import com.spring.springsecuritylab.entity.Role;
import com.spring.springsecuritylab.service.JwtUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtUtilImpl implements JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generateToken(String email, Role role){
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String extractEmail(String token){
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token).getBody().getExpiration();
    }

    @Override
    public boolean isTokenNotExpired(String token) {
        return extractExpiration(token).after(new Date());
    }

    @Override
    public boolean validateToken(String token, String email) {
        String tokenEmail = extractEmail(token);

        return tokenEmail.equals(email) && isTokenNotExpired(token);
    }
}
