package com.twtw.backend.domain.plan.client;

import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationResponse;
import com.twtw.backend.domain.plan.entity.CategoryGroupCode;
import com.twtw.backend.global.client.MapClient;
import com.twtw.backend.global.exception.WebClientResponseException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class SearchDestinationClient
        implements MapClient<SearchDestinationRequest, SearchDestinationResponse> {
    private static final Integer MAX_SIZE_PER_REQUEST = 15;
    private static final Integer DEFAULT_DISTANCE_RADIUS = 20000;

    @Qualifier("KakaoWebClient")
    private final WebClient webClient;

    @Override
    public SearchDestinationResponse request(final SearchDestinationRequest request) {
        return webClient
                .get()
                .uri(uriBuilder -> getUri(request, uriBuilder))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToMono(SearchDestinationResponse.class)
                .blockOptional()
                .orElseThrow(WebClientResponseException::new);
    }

    private URI getUri(final SearchDestinationRequest request, final UriBuilder uriBuilder) {
        final UriBuilder builder =
                uriBuilder
                        .path("search/keyword")
                        .queryParam("query", request.getQuery())
                        .queryParam("x", request.getX())
                        .queryParam("y", request.getY())
                        .queryParam("radius", DEFAULT_DISTANCE_RADIUS)
                        .queryParam("page", request.getPage())
                        .queryParam("size", MAX_SIZE_PER_REQUEST);

        final CategoryGroupCode categoryGroupCode = request.getCategoryGroupCode();

        if (categoryGroupCode.isNone()) {
            return builder.build();
        }
        return builder.queryParam("category_group_code", categoryGroupCode).build();
    }
}
