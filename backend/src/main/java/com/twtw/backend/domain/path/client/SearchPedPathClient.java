package com.twtw.backend.domain.path.client;

import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathRequest;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathResponse;
import com.twtw.backend.global.client.TmapClient;
import com.twtw.backend.global.exception.WebClientResponseException;
import com.twtw.backend.global.properties.TmapProperties;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class SearchPedPathClient extends TmapClient<SearchPedPathRequest, SearchPedPathResponse> {

    private final WebClient webClient;

    public SearchPedPathClient(final TmapProperties tmapProperties) {
        super(tmapProperties);
        this.webClient = generateWebClient();
    }

    @Override
    @CircuitBreaker(name = "backend-a", fallbackMethod = "fallback")
    public SearchPedPathResponse request(SearchPedPathRequest request) {
        return webClient
                .post()
                .uri(uri -> getPathUri(uri))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SearchPedPathResponse.class)
                .blockOptional()
                .orElseThrow(WebClientResponseException::new);
    }

    private URI getPathUri(final UriBuilder uriBuilder) {
        final UriBuilder builder = uriBuilder.queryParam("version", 1.1);
        return builder.build();
    }

    public SearchPedPathResponse fallback(final Exception e) {
        log.error("SearchPedPathClient fallback", e);
        return SearchPedPathResponse.onError();
    }
}
