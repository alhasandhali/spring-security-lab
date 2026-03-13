package com.spring.springsecuritylab.controller;

import com.spring.springsecuritylab.dto.ProfileResponse;
import com.spring.springsecuritylab.service.AuthUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final AuthUtil authUtil;

    public UserController(AuthUtil authUtil) {
        this.authUtil = authUtil;
    }

    @GetMapping("/test/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String testAdmin() {
        return "Test ADMIN running.....";
    }

    @GetMapping("/test/user")
    @PreAuthorize("hasAuthority('USER')")
    public String testUser() {
        return "Test USER running.....";
    }

    @GetMapping("/profile")
    public ProfileResponse testProfile() {
        return authUtil.getCurrentUserEmail();
    }
}
