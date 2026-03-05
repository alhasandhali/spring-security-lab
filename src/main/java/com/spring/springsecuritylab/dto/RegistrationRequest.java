package com.spring.springsecuritylab.dto;

import com.spring.springsecuritylab.entity.Role;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RegistrationRequest {
    private String email;
    private String password;
    private Role role;
    private LocalDateTime createdAt;
}
