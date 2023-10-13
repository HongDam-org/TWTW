package com.twtw.backend.config.socket;

import com.twtw.backend.global.properties.RabbitMQProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {
    private final RabbitMQProperties rabbitMQProperties;

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.setPathMatcher(new AntPathMatcher("."));

        registry.enableStompBrokerRelay("/topic", "/queue", "/exchange", "/amq/queue")
                .setRelayHost(rabbitMQProperties.getHost())
                .setRelayPort(61613)
                .setClientPasscode(rabbitMQProperties.getPassword())
                .setClientLogin(rabbitMQProperties.getUsername());

        registry.setApplicationDestinationPrefixes("/pub");
    }
}
