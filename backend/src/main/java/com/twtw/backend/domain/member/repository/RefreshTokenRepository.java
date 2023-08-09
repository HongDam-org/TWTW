package com.twtw.backend.domain.member.repository;

import com.twtw.backend.domain.member.entity.RefreshToken;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {}
