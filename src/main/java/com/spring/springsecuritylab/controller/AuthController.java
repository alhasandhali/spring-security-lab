package com.spring.springsecuritylab.controller;

import com.spring.springsecuritylab.dto.RegisterResponse;
import com.spring.springsecuritylab.dto.LoginRequest;
import com.spring.springsecuritylab.dto.LoginResponse;
import com.spring.springsecuritylab.dto.RegistrationRequest;
import com.spring.springsecuritylab.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegistrationRequest registrationRequest) {
        return authService.register(registrationRequest.getEmail(), registrationRequest.getPassword());
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @GetMapping
    public String auth() {
        return "Testing auth API.....";
    }
}
