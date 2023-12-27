package com.twtw.backend.domain.member.repository;

import com.twtw.backend.domain.member.entity.DeviceToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeviceTokenRepository extends JpaRepository<DeviceToken, UUID> {
}
