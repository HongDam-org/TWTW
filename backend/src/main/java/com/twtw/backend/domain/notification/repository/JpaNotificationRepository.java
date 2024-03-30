package com.twtw.backend.domain.notification.repository;

import com.twtw.backend.domain.notification.entity.Notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaNotificationRepository
        extends JpaRepository<Notification, UUID>, NotificationRepository {}
