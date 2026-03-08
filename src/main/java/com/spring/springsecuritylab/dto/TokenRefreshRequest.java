package com.spring.springsecuritylab.dto;

import com.spring.springsecuritylab.entity.RefreshToken;
import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
