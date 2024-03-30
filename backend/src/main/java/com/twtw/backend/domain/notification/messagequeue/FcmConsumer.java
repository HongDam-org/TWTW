package com.twtw.backend.domain.notification.messagequeue;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.twtw.backend.domain.notification.dto.NotificationRequest;
import com.twtw.backend.domain.notification.entity.Notification;
import com.twtw.backend.domain.notification.repository.NotificationRepository;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class FcmConsumer {
    private final FirebaseMessaging firebaseMessaging;
    private final NotificationRepository notificationRepository;

    public FcmConsumer(
            FirebaseMessaging firebaseMessaging, NotificationRepository notificationRepository) {
        this.firebaseMessaging = firebaseMessaging;
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    @RabbitListener(queues = "notification.queue")
    public void sendNotification(final NotificationRequest request)
            throws FirebaseMessagingException {
        firebaseMessaging.send(request.toMessage());

        notificationRepository
                .findById(UUID.fromString(request.getNotificationId()))
                .ifPresent(Notification::complete);
    }
}
