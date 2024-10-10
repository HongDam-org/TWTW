package com.twtw.backend.domain.notification.messagequeue;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.rabbitmq.client.Channel;
import com.twtw.backend.domain.notification.dto.NotificationRequest;
import com.twtw.backend.domain.notification.entity.Notification;
import com.twtw.backend.domain.notification.exception.NotificationException;
import com.twtw.backend.domain.notification.repository.NotificationRepository;
import com.twtw.backend.global.constant.RabbitMQConstant;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
    private final RabbitTemplate rabbitTemplate;

    @Transactional(noRollbackFor = {NotificationException.class})
    @RabbitListener(
            queues = "notification.queue",
            containerFactory = "rabbitListenerContainerFactory")
    public void sendNotification(
            final NotificationRequest request,
            final Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) final long tag)
            throws IOException {
        try {
            send(request);
            channel.basicAck(tag, false);
        } catch (final NotificationException e) {
            channel.basicNack(tag, false, false);
        } catch (final Exception e) {
            rabbitTemplate.convertAndSend(
                    RabbitMQConstant.NOTIFICATION_RETRY_EXCHANGE.getName(),
                    RabbitMQConstant.NOTIFICATION_RETRY_ROUTING_KEY.getName(),
                    request);
            channel.basicAck(tag, false);
        }
    }

    @Transactional(noRollbackFor = {NotificationException.class})
    @RabbitListener(
            queues = "notification.retry.queue",
            containerFactory = "retryRabbitListenerContainerFactory")
    public void retrySendNotification(
            final NotificationRequest request,
            final Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) final long tag)
            throws IOException {
        try {
            send(request);
            channel.basicAck(tag, false);
        } catch (final NotificationException e) {
            channel.basicNack(tag, false, false);
        } catch (final Exception e) {
            channel.basicNack(tag, false, true);
        }
    }

    private void send(final NotificationRequest request) throws FirebaseMessagingException {
        final Optional<Notification> notification =
                notificationRepository.findById(UUID.fromString(request.getNotificationId()));

        if (notification.isEmpty()) {
            throw new NotificationException();
        }
        firebaseMessaging.send(request.toMessage());
        notification.get().complete();
    }
}
