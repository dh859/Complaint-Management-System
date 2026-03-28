package com.cms.cmsapp.auth.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.cmsapp.auth.entity.RefreshToken;
import com.cms.cmsapp.auth.repository.RefreshTokenRepo;
import com.cms.cmsapp.common.exceptions.TokenRefreshException;
import com.cms.cmsapp.user.repository.UserRepo;

@Service
public class RefreshTokenService {

    // @Value("${app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs=864000L;

    @Autowired
    private RefreshTokenRepo refreshTokenRepository;

    @Autowired
    private UserRepo userRepository;

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusSeconds(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token expired. Please login again.");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser_UserId(userRepository.findById(userId).get().getUserId());
    }
}
