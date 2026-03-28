package com.cms.cmsapp.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cms.cmsapp.auth.entity.RefreshToken;


public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
    public Optional<RefreshToken> findByToken(String token);
    public void deleteByUser_UserId(Long userId);
}
