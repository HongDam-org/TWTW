package com.twtw.backend.domain.notification.dto;

import com.google.firebase.messaging.Notification;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    @NotBlank private String deviceToken;

    private String title;

    private String body;

    public Notification toNotification() {
        return new Notification(title, body);
    }
}
