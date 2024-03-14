package com.twtw.backend.domain.member.repository;

import com.twtw.backend.domain.member.entity.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaRefreshTokenRepository
        extends JpaRepository<RefreshToken, UUID>, RefreshTokenRepository {}
