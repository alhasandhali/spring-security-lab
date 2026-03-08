package com.spring.springsecuritylab.service;

import com.spring.springsecuritylab.entity.RefreshToken;
import com.spring.springsecuritylab.entity.User;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(User user);
    void verifyRefreshToken(RefreshToken token);
    RefreshToken findByToken(String token);
    void deleteByToken(String token);
}
