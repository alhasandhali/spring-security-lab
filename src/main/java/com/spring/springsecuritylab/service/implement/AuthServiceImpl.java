package com.spring.springsecuritylab.service.implement;

import com.spring.springsecuritylab.dto.RegisterResponse;
import com.spring.springsecuritylab.dto.LoginResponse;
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
        registerResponse.setMessage("User registered successfully");
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
        loginResponse.setToken(jwtUtil.generateToken(user.get().getEmail(), user.get().getRole()));
        loginResponse.setMessage("Logged in successfully");
        refreshTokenService.createRefreshToken(user.get());
        return loginResponse;
    }
}
