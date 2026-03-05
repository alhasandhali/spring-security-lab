package com.spring.springsecuritylab.service.implement;

import com.spring.springsecuritylab.entity.User;
import com.spring.springsecuritylab.repository.UserRepository;
import com.spring.springsecuritylab.service.AuthService;
import com.spring.springsecuritylab.service.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public String register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "User already exists";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @Override
    public String login(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return "User not found";
        } else if (!passwordEncoder.matches(password, user.get().getPassword())) {
            return "Wrong password";
        }

        return "Token: " + jwtUtil.generateToken(user.get().getEmail());
    }
}
