package com.spring.springsecuritylab.service.implement;

import com.spring.springsecuritylab.dto.ProfileResponse;
import com.spring.springsecuritylab.entity.Role;
import com.spring.springsecuritylab.exception.UnauthorizedException;
import com.spring.springsecuritylab.service.AuthUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthUtilImpl implements AuthUtil {
    @Override
    public ProfileResponse getCurrentUserEmail() {
        ProfileResponse profileResponse = new ProfileResponse();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("User is not logged in or session has expired.");
        }
        profileResponse.setEmail(authentication.getName());

        authentication.getAuthorities().stream()
                .findFirst()
                .ifPresent(grantedAuthority -> {
                    profileResponse.setRole(Role.valueOf(grantedAuthority.getAuthority()));
                });
        return profileResponse;
    }
}
