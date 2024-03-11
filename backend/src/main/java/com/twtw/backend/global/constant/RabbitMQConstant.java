package com.twtw.backend.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RabbitMQConstant {
    LOCATION_QUEUE("map.queue.*"),
    LOCATION_EXCHANGE("map"),
    LOCATION_ROUTING_KEY("location.*"),
    LOCATION_ROUTING_KEY_PREFIX("location."),
    NOTIFICATION_QUEUE("notification.queue"),
    NOTIFICATION_EXCHANGE("notification"),
    NOTIFICATION_ROUTING_KEY("notification"),
    DEAD_LETTER_QUEUE("deadletter.queue"),
    DEAD_LETTER_EXCHANGE("deadletter"),
    DEAD_LETTER_ROUTING_KEY("deadletter");

    private final String name;
}
