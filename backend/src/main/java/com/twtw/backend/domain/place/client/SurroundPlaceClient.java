package com.twtw.backend.domain.place.client;

import com.twtw.backend.domain.place.dto.client.SurroundPlaceRequest;
import com.twtw.backend.domain.place.dto.client.SurroundPlaceResponse;
import com.twtw.backend.domain.place.entity.CategoryGroupCode;
import com.twtw.backend.global.client.KakaoMapClient;
import com.twtw.backend.global.exception.WebClientResponseException;
import com.twtw.backend.global.properties.KakaoProperties;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class SurroundPlaceClient
        extends KakaoMapClient<SurroundPlaceRequest, SurroundPlaceResponse> {
    private static final Integer MAX_SIZE_PER_REQUEST = 15;
    private static final Integer DEFAULT_DISTANCE_RADIUS = 1000;
    private final WebClient webClient;

    public SurroundPlaceClient(final KakaoProperties kakaoProperties) {
        super(kakaoProperties);
        this.webClient = generateWebClient();
    }

    @Override
    @CircuitBreaker(name = "backend-a", fallbackMethod = "fallback")
    public SurroundPlaceResponse request(final SurroundPlaceRequest request) {
        return webClient
                .get()
                .uri(uriBuilder -> getUri(request, uriBuilder))
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(SurroundPlaceResponse.class)
                .blockOptional()
                .orElseThrow(WebClientResponseException::new);
    }

    public SurroundPlaceResponse fallback(final Exception e) {
        log.error("SurroundPlaceClient fallback", e);
        return SurroundPlaceResponse.onError();
    }

    private URI getUri(final SurroundPlaceRequest request, final UriBuilder uriBuilder) {
        return uriBuilder
                .path("search/category")
                .queryParam("x", request.getLongitude())
                .queryParam("y", request.getLatitude())
                .queryParam("radius", DEFAULT_DISTANCE_RADIUS)
                .queryParam("page", request.getPage())
                .queryParam("size", MAX_SIZE_PER_REQUEST)
                .queryParam("category_group_code", CategoryGroupCode.CE7)
                .build();
    }
}
