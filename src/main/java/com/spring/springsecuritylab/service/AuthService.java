package com.spring.springsecuritylab.service;


import com.spring.springsecuritylab.entity.User;

public interface AuthService {
    String register(User user);
    String login(String email, String password);
}
