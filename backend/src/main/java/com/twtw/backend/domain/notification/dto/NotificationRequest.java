package com.twtw.backend.domain.notification.dto;

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

    private String deviceToken;
    private String title;
    private String body;

    public Message toMessage() {
        return Message.builder().setToken(deviceToken).setNotification(toNotification()).build();
    }

    private Notification toNotification() {
        return new Notification(title, body);
    }
}
