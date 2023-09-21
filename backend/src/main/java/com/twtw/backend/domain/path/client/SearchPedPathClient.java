package com.twtw.backend.domain.path.client;

import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathRequest;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathResponse;
import com.twtw.backend.global.client.MapClient;
import com.twtw.backend.global.exception.WebClientResponseException;
import com.twtw.backend.global.properties.TmapProperties;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Component
public class SearchPedPathClient implements MapClient<SearchPedPathRequest, SearchPedPathResponse> {

    private final WebClient webClient;
    private final TmapProperties tmapProperties;

    public SearchPedPathClient(final WebClient webClient, final TmapProperties tmapProperties) {
        this.webClient = webClient;
        this.tmapProperties = tmapProperties;
    }

    @Override
    public SearchPedPathResponse request(SearchPedPathRequest request) {
        return webClient
                .post()
                .uri(uri -> getPathUri(uri))
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .header("appKey", tmapProperties.getAppKey())
                .bodyValue(request)
                .retrieve()
                .bodyToMono(SearchPedPathResponse.class)
                .blockOptional()
                .orElseThrow(WebClientResponseException::new);
    }

    private URI getPathUri(final UriBuilder uriBuilder) {
        final UriBuilder builder =
                uriBuilder.path(tmapProperties.getUrl()).queryParam("version", 1);

        return builder.build();
    }
}
