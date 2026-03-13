package com.spring.springsecuritylab.dto;

import com.spring.springsecuritylab.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {
    private long id;
    private String email;
    private Role role;
}
