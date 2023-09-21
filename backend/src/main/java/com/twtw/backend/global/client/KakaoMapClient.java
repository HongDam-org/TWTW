package com.twtw.backend.global.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twtw.backend.global.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@RequiredArgsConstructor
public abstract class KakaoMapClient<T, R> implements MapClient<T, R> {
    private static final Integer NO_LIMIT = -1;
    private final ObjectMapper objectMapper;
    protected final KakaoProperties kakaoProperties;

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
                .baseUrl(kakaoProperties.getUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION,
                        kakaoProperties.getHeaderPrefix() + kakaoProperties.getKey())
                .exchangeStrategies(exchangeStrategies)
                .build();
    }
}
