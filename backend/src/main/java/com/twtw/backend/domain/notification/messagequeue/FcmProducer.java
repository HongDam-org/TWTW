package com.twtw.backend.domain.notification.messagequeue;

import com.twtw.backend.domain.notification.dto.NotificationRequest;
import com.twtw.backend.global.constant.RabbitMQConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FcmProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendNotification(final NotificationRequest request) {
        rabbitTemplate.convertAndSend(
                RabbitMQConstant.NOTIFICATION_EXCHANGE.getName(),
                RabbitMQConstant.NOTIFICATION_ROUTING_KEY.getName(),
                request);
    }
}
