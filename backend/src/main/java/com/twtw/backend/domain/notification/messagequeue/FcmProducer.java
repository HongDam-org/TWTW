package com.twtw.backend.domain.notification.messagequeue;

import com.twtw.backend.domain.notification.dto.NotificationRequest;
import com.twtw.backend.domain.notification.entity.Notification;
import com.twtw.backend.domain.notification.entity.NotificationType;
import com.twtw.backend.domain.notification.mapper.NotificationMapper;
import com.twtw.backend.domain.notification.repository.NotificationRepository;
import com.twtw.backend.global.constant.RabbitMQConstant;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FcmProducer {

    private final RabbitTemplate rabbitTemplate;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public void sendNotification(final NotificationRequest request, final NotificationType type) {
        final UUID id =
                notificationRepository
                        .save(
                                new Notification(
                                        request.getTitle(),
                                        request.getBody(),
                                        request.getId(),
                                        request.getDeviceToken(),
                                        type))
                        .getId();

        request.setNotificationId(id.toString());

        sendToQueue(request);
    }

    private void sendToQueue(final NotificationRequest request) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstant.NOTIFICATION_EXCHANGE.getName(),
                RabbitMQConstant.NOTIFICATION_ROUTING_KEY.getName(),
                request);
    }

    public void sendFailedNotification(final List<UUID> ids) {
        notificationRepository.findAllByIdIn(ids).stream()
                .map(notificationMapper::toNotificationRequest)
                .forEach(this::sendToQueue);
    }
}
