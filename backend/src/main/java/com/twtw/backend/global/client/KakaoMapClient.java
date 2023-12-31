package com.twtw.backend.global.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.twtw.backend.global.properties.KakaoProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public abstract class KakaoMapClient<T, R> implements MapClient<T, R> {
    private static final Integer NO_LIMIT = -1;
    private static final String HEADER_PREFIX = "KakaoAK ";
    protected final KakaoProperties kakaoProperties;

    protected WebClient generateWebClient() {
        final ObjectMapper objectMapper = objectMapper();

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
                .baseUrl(kakaoProperties.getUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, HEADER_PREFIX + kakaoProperties.getKey())
                .exchangeStrategies(exchangeStrategies)
                .build();
    }

    private ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    }
}
