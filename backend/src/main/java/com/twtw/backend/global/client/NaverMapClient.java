package com.twtw.backend.global.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.twtw.backend.config.mapper.CompositePropertyNamingStrategy;
import com.twtw.backend.global.properties.NaverProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public abstract class NaverMapClient<T, R> implements MapClient<T, R> {
    private static final Integer NO_LIMIT = -1;
    private final ObjectMapper objectMapper;
    protected final NaverProperties naverProperties;

    protected WebClient generateWebClient() {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        final ExchangeStrategies exchangeStrategies =
                ExchangeStrategies.builder()
                        .codecs(
                                configurer -> {
                                    configurer.defaultCodecs().maxInMemorySize(NO_LIMIT);
                                    configurer
                                            .defaultCodecs()
                                            .jackson2JsonDecoder(
                                                    new Jackson2JsonDecoder(objectMapper));
                                    configurer
                                            .defaultCodecs()
                                            .jackson2JsonEncoder(
                                                    new Jackson2JsonEncoder(objectMapper));
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
