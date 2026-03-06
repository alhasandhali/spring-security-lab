package com.spring.springsecuritylab.service;


import com.spring.springsecuritylab.dto.RegisterResponse;
import com.spring.springsecuritylab.dto.LoginResponse;

public interface AuthService {
    RegisterResponse register(String email, String password);
    LoginResponse login(String email, String password);
}
