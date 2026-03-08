package com.spring.springsecuritylab.exception;

public class RefreshTokenExpiryException extends RuntimeException{
    public RefreshTokenExpiryException(String message) {
        super(message);
    }
}
