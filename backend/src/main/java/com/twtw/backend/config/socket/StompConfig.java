package com.twtw.backend.config.socket;

import com.twtw.backend.global.properties.RabbitMQProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class StompConfig implements WebSocketMessageBrokerConfigurer {

    private static final int HEART_BEAT_INTERVAL = 10_000;
    private final RabbitMQProperties rabbitMQProperties;

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/location").setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.setPathMatcher(new AntPathMatcher("."));

        registry.enableStompBrokerRelay("/topic", "/queue", "/exchange", "/amq/queue")
                .setRelayHost(rabbitMQProperties.getHost())
                .setRelayPort(61613)
                .setClientPasscode(rabbitMQProperties.getPassword())
                .setClientLogin(rabbitMQProperties.getUsername())
                .setSystemHeartbeatSendInterval(HEART_BEAT_INTERVAL)
                .setSystemHeartbeatReceiveInterval(HEART_BEAT_INTERVAL);

        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void configureClientInboundChannel(final ChannelRegistration registration) {
        registration.taskExecutor(inboundTaskExecutor());
    }

    @Override
    public void configureClientOutboundChannel(final ChannelRegistration registration) {
        registration.taskExecutor(outboundTaskExecutor());
    }

    @Bean
    public ThreadPoolTaskExecutor inboundTaskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setThreadNamePrefix("stomp-inbound-");
        executor.initialize();
        return executor;
    }

    @Bean
    public ThreadPoolTaskExecutor outboundTaskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setThreadNamePrefix("stomp-outbound-");
        executor.initialize();
        return executor;
    }
}
