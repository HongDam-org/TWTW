package com.twtw.backend.domain.notification.messagequeue;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.twtw.backend.domain.notification.dto.NotificationRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FcmConsumer {
    private final FirebaseMessaging firebaseMessaging;

    public FcmConsumer(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @RabbitListener(queues = "notification.queue")
    public void sendNotification(final NotificationRequest request) throws FirebaseMessagingException {
        firebaseMessaging.send(request.toMessage());
    }
}
