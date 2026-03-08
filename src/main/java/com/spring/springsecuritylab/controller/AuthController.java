package com.spring.springsecuritylab.controller;

import com.spring.springsecuritylab.dto.*;
import com.spring.springsecuritylab.entity.RefreshToken;
import com.spring.springsecuritylab.entity.User;
import com.spring.springsecuritylab.service.AuthService;
import com.spring.springsecuritylab.service.JwtUtil;
import com.spring.springsecuritylab.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public RegisterResponse registerUser(@RequestBody RegistrationRequest registrationRequest) {
        return authService.register(registrationRequest.getEmail(), registrationRequest.getPassword());
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(@RequestBody LogoutRequest logoutRequest) {
        RefreshToken token = refreshTokenService.findByToken(logoutRequest.getRefreshToken());
        refreshTokenService.deleteByToken(token.getToken());
        return ResponseEntity.ok(new LogoutResponse("Logged Out Successfully."));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request) {
        String requestToken = request.getRefreshToken();
        RefreshToken refreshToken = refreshTokenService.findByToken(requestToken);
        refreshTokenService.verifyRefreshToken(refreshToken);
        User user = refreshToken.getUser();
        String newAccessToken = jwtUtil.generateToken(user.getEmail(),  user.getRole());
        return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, requestToken));
    }

    @GetMapping
    public String auth() {
        return "Testing auth API.....";
    }
}
