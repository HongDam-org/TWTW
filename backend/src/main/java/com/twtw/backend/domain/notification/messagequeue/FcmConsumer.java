package com.twtw.backend.domain.notification.messagequeue;

import com.google.firebase.messaging.FirebaseMessaging;
import com.rabbitmq.client.Channel;
import com.twtw.backend.domain.notification.dto.NotificationRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class FcmConsumer {
    private final FirebaseMessaging firebaseMessaging;

    public FcmConsumer(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }

    @RabbitListener(queues = "notification.queue")
    public void sendNotification(
            final NotificationRequest request,
            final Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) final long tag) throws IOException {
        try {
            firebaseMessaging.send(request.toMessage());
        } catch (final Exception e) {
            channel.basicNack(tag, false, false);
        }
    }
}
