package com.twtw.backend.domain.notification.dto;

import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {

    private static final ApnsConfig APNS_CONFIG = ApnsConfig.builder()
            .putHeader("apns-priority", "10")
                .setAps(Aps.builder()
                        .setBadge(1)
                        .build()).build();

    private String deviceToken;
    private String title;
    private String body;

    public Message toMessage() {
        return Message.builder()
                .setApnsConfig(APNS_CONFIG)
                .setToken(deviceToken)
                .setNotification(toNotification())
                .build();
    }

    private Notification toNotification() {
        return new Notification(title, body);
    }
}
