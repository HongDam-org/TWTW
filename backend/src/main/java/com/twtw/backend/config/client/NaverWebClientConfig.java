package com.twtw.backend.config.client;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class NaverWebClientConfig {
    private static final String HEADER_CLIENT_ID = "X-NCP-APIGW-API-KEY-ID";
    private static final String HEADER_CLIENT_SECRET = "X-NCP-APIGW-API-KEY";
    private final ObjectMapper objectMapper;

    @Autowired
    public NaverWebClientConfig(@Qualifier("naverObjectMapper") ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean(name = "NaverWebClient")
    public WebClient webClient(
            @Value("${naver-map.url}") final String url,
            @Value("${naver-map.id}") final String clientId,
            @Value("${naver-map.secret}") final String secretKey) {

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
                .baseUrl(url)
                .defaultHeader(HEADER_CLIENT_ID, clientId)
                .defaultHeader(HEADER_CLIENT_SECRET, secretKey)
                .build();
    }
}
