package com.spring.springsecuritylab.service;


import com.spring.springsecuritylab.dto.LogoutResponse;
import com.spring.springsecuritylab.dto.RegisterResponse;
import com.spring.springsecuritylab.dto.LoginResponse;
import com.spring.springsecuritylab.dto.TokenRefreshResponse;

public interface AuthService {
    RegisterResponse register(String email, String password);
    LoginResponse login(String email, String password);
    LogoutResponse logout(String token);
    TokenRefreshResponse refreshToken(String token);
}
