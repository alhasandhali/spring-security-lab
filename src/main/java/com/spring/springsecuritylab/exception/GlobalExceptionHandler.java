package com.spring.springsecuritylab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongPasswordException.class)
    public ResponseEntity<Object> handleWrongPasswordException(WrongPasswordException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", HttpStatus.FORBIDDEN.value());
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(RefreshTokenExpiryException.class)
    public ResponseEntity<Object> handleRefreshTokenExpiryException(RefreshTokenExpiryException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", HttpStatus.UNAUTHORIZED.value());
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RefreshTokenNotFound.class)
    public ResponseEntity<Object> handleRefreshTokenNotFound(RefreshTokenNotFound ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", HttpStatus.NOT_FOUND.value());
        response.put("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
