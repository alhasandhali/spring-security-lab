package com.spring.springsecuritylab.service.implement;

import com.spring.springsecuritylab.entity.RefreshToken;
import com.spring.springsecuritylab.entity.User;
import com.spring.springsecuritylab.exception.RefreshTokenExpiryException;
import com.spring.springsecuritylab.exception.RefreshTokenNotFound;
import com.spring.springsecuritylab.repository.RefreshTokenRepository;
import com.spring.springsecuritylab.repository.UserRepository;
import com.spring.springsecuritylab.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenServiceImpl(UserRepository userRepository,  RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusDays(7))
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void verifyRefreshToken(RefreshToken token) {
        if (token.getExpiredAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiryException("Refresh token expired");
        }
    }

    @Override
    public RefreshToken findByToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken.isPresent()) {
            return refreshToken.get();
        }
        throw new RefreshTokenNotFound("Refresh token not found");
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
