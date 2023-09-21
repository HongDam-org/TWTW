package com.twtw.backend.global.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twtw.backend.global.properties.NaverProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public abstract class NaverMapClient<T, R> implements MapClient<T, R> {
    private static final Integer NO_LIMIT = -1;
    private final ObjectMapper objectMapper;
    protected final NaverProperties naverProperties;

    protected WebClient generateWebClient() {
        final ExchangeStrategies exchangeStrategies =
                ExchangeStrategies.builder()
                        .codecs(
                                configurer -> {
                                    configurer.defaultCodecs().maxInMemorySize(NO_LIMIT);
                                    configurer
                                            .defaultCodecs()
                                            .jackson2JsonDecoder(
                                                    new Jackson2JsonDecoder(objectMapper));
                                })
                        .build();

        return WebClient.builder()
                .baseUrl(naverProperties.getUrl())
                .defaultHeader(naverProperties.getHeaderClientId(), naverProperties.getId())
                .defaultHeader(naverProperties.getHeaderClientSecret(), naverProperties.getSecret())
                .exchangeStrategies(exchangeStrategies)
                .build();
    }
}
