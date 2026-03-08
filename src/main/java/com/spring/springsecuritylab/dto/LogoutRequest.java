package com.spring.springsecuritylab.dto;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;
}
