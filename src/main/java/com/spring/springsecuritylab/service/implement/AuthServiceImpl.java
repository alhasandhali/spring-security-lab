package com.spring.springsecuritylab.service.implement;

import com.spring.springsecuritylab.dto.*;
import com.spring.springsecuritylab.entity.RefreshToken;
import com.spring.springsecuritylab.entity.Role;
import com.spring.springsecuritylab.entity.User;
import com.spring.springsecuritylab.exception.UserNotFoundException;
import com.spring.springsecuritylab.exception.WrongPasswordException;
import com.spring.springsecuritylab.repository.UserRepository;
import com.spring.springsecuritylab.service.AuthService;
import com.spring.springsecuritylab.service.JwtUtil;
import com.spring.springsecuritylab.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil,
                           RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public RegisterResponse register(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        RegisterResponse registerResponse = new RegisterResponse();
        if (optionalUser.isPresent()) {
            registerResponse.setMessage("User already exists");
            return registerResponse;
        }
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setRole(Role.USER);
        newUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(newUser);

        User regiUser = userRepository.findByEmail(email).get();

        UserResponse userResponse = new UserResponse(regiUser.getId(), regiUser.getEmail(), regiUser.getRole());
        registerResponse.setMessage("User registered successfully");
        registerResponse.setStatus("success");
        registerResponse.setUserResponse(userResponse);
        return registerResponse;
    }

    @Override
    public LoginResponse login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        LoginResponse loginResponse = new LoginResponse();
        if (user.isEmpty()) {
            throw new UserNotFoundException("User not found");
        } else if (!passwordEncoder.matches(password, user.get().getPassword())) {
            throw new WrongPasswordException("Wrong password");
        }
        UserResponse  userResponse = new UserResponse(user.get().getId(), user.get().getEmail(), user.get().getRole());
        loginResponse.setAccessToken(jwtUtil.generateToken(user.get().getEmail(), user.get().getRole()));
        loginResponse.setRefreshToken(refreshTokenService.createRefreshToken(user.get()));
        loginResponse.setMessage("Logged in successfully");
        loginResponse.setStatus("success");
        loginResponse.setUser(userResponse);
        refreshTokenService.createRefreshToken(user.get());
        return loginResponse;
    }

    @Override
    @Transactional
    public LogoutResponse logout(String token) {
        RefreshToken refreshToken = refreshTokenService.findByToken(token);
        if (refreshToken != null) {
            refreshTokenService.deleteByToken(refreshToken.getToken());
            LogoutResponse logoutResponse = new LogoutResponse();
            logoutResponse.setMessage("Logged out successfully");
            logoutResponse.setStatus("success");
            return logoutResponse;
        }
        throw new RuntimeException();
    }

    @Override
    public TokenRefreshResponse refreshToken(String token) {
        RefreshToken refreshToken = refreshTokenService.findByToken(token);
        if (refreshToken != null) {
            refreshTokenService.verifyRefreshToken(refreshToken);
            User user = refreshToken.getUser();
            String newAccessToken = jwtUtil.generateToken(user.getEmail(),  user.getRole());
            return new TokenRefreshResponse(newAccessToken, refreshToken.getToken());
        }
        throw new RuntimeException();
    }
}
