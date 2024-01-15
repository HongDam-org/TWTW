package com.twtw.backend.config.socket;

import com.twtw.backend.config.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompHandler implements ChannelInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(final Message<?> message, final MessageChannel channel) {
        final StompHeaderAccessor acessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == acessor.getCommand()) {
            final Optional<String> headerValue =
                    Optional.ofNullable(acessor.getFirstNativeHeader(AUTHORIZATION_HEADER));
            resolveToken(headerValue)
                    .ifPresent(
                            header -> {
                                tokenProvider.validateToken(header);
                                SecurityContextHolder.getContext()
                                        .setAuthentication(tokenProvider.getAuthentication(header));
                            });
        }
        return message;
    }

    private Optional<String> resolveToken(final Optional<String> headerValue) {
        return headerValue.map(
                header -> {
                    if (header.startsWith(BEARER_PREFIX)) {
                        return header.substring(7);
                    }
                    return header;
                });
    }
}
