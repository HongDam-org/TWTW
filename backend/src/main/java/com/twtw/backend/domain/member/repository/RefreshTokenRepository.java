package com.twtw.backend.domain.member.repository;

import com.twtw.backend.domain.member.entity.RefreshToken;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository {
    Optional<RefreshToken> findByTokenKey(final String tokenKey);

    RefreshToken save(final RefreshToken refreshToken);
}
