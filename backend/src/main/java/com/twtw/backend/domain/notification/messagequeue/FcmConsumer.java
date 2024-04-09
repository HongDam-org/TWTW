package com.twtw.backend.domain.notification.messagequeue;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.rabbitmq.client.Channel;
import com.twtw.backend.domain.notification.dto.NotificationRequest;
import com.twtw.backend.domain.notification.entity.Notification;
import com.twtw.backend.domain.notification.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FcmConsumer {
    private final FirebaseMessaging firebaseMessaging;
    private final NotificationRepository notificationRepository;

    @Transactional
    @RabbitListener(queues = "notification.queue")
    public void sendNotification(
            final NotificationRequest request,
            final Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) final long tag)
            throws FirebaseMessagingException, IOException {
        firebaseMessaging.send(request.toMessage());

        final Optional<Notification> notification =
                notificationRepository.findById(UUID.fromString(request.getNotificationId()));

        if (notification.isPresent()) {
            notification.get().complete();
            return;
        }
        channel.basicNack(tag, false, false);
    }
}
