package com.spring.springsecuritylab.dto;

import com.spring.springsecuritylab.entity.Role;
import lombok.Data;

@Data
public class ProfileResponse {
    private String email;
    private Role role;
}
