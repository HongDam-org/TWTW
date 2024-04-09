package com.twtw.backend.domain.notification.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification implements Auditable {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id = UlidCreator.getMonotonicUlid().toUuid();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private String idInfo;

    @Column(nullable = false)
    private String deviceToken;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private Boolean isCompleted = false;

    @Setter private BaseTime baseTime;

    @Builder
    public Notification(
            final String title,
            final String body,
            final String idInfo,
            final String deviceToken,
            final NotificationType type) {
        this.title = title;
        this.body = body;
        this.idInfo = idInfo;
        this.deviceToken = deviceToken;
        this.type = type;
    }

    public void complete() {
        this.isCompleted = true;
    }
}
