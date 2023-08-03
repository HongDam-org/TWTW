package com.twtw.backend.config.security.repository;

import com.twtw.backend.config.security.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
}
