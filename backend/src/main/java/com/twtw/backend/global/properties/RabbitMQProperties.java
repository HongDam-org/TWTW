package com.twtw.backend.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitMQProperties {
    private final String host;
    private final Integer port;
    private final String username;
    private final String password;
}
