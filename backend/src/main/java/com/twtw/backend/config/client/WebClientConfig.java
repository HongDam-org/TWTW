package com.twtw.backend.config.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final ObjectMapper objectMapper;

    @Bean
    public WebClient webClient() {
        final ExchangeStrategies exchangeStrategies =
                ExchangeStrategies.builder()
                        .codecs(
                                configurer -> {
                                    configurer.defaultCodecs().maxInMemorySize(-1);
                                    configurer
                                            .defaultCodecs()
                                            .jackson2JsonDecoder(
                                                    new Jackson2JsonDecoder(objectMapper));
                                })
                        .build();

        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .build();
    }
}
