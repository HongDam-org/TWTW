package com.twtw.backend.domain.plan.client;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationResponse;
import com.twtw.backend.global.client.KakaoMapClient;
import com.twtw.backend.global.exception.WebClientResponseException;
import com.twtw.backend.global.properties.KakaoProperties;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
public class SearchDestinationClient
        extends KakaoMapClient<SearchDestinationRequest, SearchDestinationResponse> {
    private static final Integer MAX_SIZE_PER_REQUEST = 15;
    private static final Integer DEFAULT_DISTANCE_RADIUS = 20000;
    private final WebClient webClient;

    public SearchDestinationClient(final KakaoProperties kakaoProperties) {
        super(kakaoProperties);
        this.webClient = generateWebClient();
    }

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
                        .queryParam("radius", DEFAULT_DISTANCE_RADIUS)
                        .queryParam("page", request.getPage())
                        .queryParam("size", MAX_SIZE_PER_REQUEST);

        final CategoryGroupCode categoryGroupCode = request.getCategoryGroupCode();
        final Double x = request.getX();
        final Double y = request.getY();

        if (x != null && y != null) {
            builder.queryParam("x", Double.toString(x)).queryParam("y", Double.toString(y));
        }
        if (categoryGroupCode.isNone()) {
            return builder.build();
        }
        return builder.queryParam("category_group_code", categoryGroupCode).build();
    }
}
