package com.twtw.backend.domain.notification.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.twtw.backend.domain.notification.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FcmService {

    @Autowired
    private final FirebaseMessaging firebaseMessaging;

    public void sendNotification(NotificationRequest request) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(request.getDeviceToken())
                .setNotification(request.toNotification())
                .build();

        firebaseMessaging.send(message);
    }
}
