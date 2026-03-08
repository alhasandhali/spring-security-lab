package com.spring.springsecuritylab.exception;

public class RefreshTokenNotFound extends RuntimeException {
    public RefreshTokenNotFound(String message) {
        super(message);
    }
}
