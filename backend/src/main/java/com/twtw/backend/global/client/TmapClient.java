package com.twtw.backend.global.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.twtw.backend.global.properties.TmapProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public abstract class TmapClient<T, R> implements MapClient<T, R> {
    private static final Integer NO_LIMIT = -1;
    private static final String APP_KEY_HEADER = "appKey";
    protected final TmapProperties tmapProperties;

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
                .baseUrl(tmapProperties.getUrl())
                .defaultHeader(APP_KEY_HEADER, tmapProperties.getAppKey())
                .exchangeStrategies(exchangeStrategies)
                .build();
    }

    private ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
                .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
    }
}
