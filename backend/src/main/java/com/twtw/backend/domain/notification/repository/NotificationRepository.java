package com.twtw.backend.domain.notification.repository;

import com.twtw.backend.domain.notification.entity.Notification;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NotificationRepository {
    Notification save(final Notification notification);
    Optional<Notification> findById(final UUID id);
}
