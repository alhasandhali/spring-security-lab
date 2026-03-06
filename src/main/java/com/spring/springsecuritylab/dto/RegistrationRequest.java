package com.spring.springsecuritylab.dto;

import com.spring.springsecuritylab.entity.Role;
import lombok.Data;

@Data
public class RegistrationRequest {
    private String email;
    private String password;
}
