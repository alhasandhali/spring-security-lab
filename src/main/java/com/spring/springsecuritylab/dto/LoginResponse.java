package com.spring.springsecuritylab.dto;

import com.spring.springsecuritylab.entity.RefreshToken;
import lombok.Data;

@Data
public class LoginResponse {
    private String status;
    private String accessToken;
    private RefreshToken refreshToken;
    private String message;
    private UserResponse user;
}
