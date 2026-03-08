package com.spring.springsecuritylab.service;

import com.spring.springsecuritylab.dto.ProfileResponse;

public interface AuthUtil {
    ProfileResponse getCurrentUserEmail();
}
