package com.twtw.backend.domain.path.client;

import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathRequest;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathResponse;
import com.twtw.backend.global.client.MapClient;
import com.twtw.backend.global.properties.TmapProperties;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
        return null;
    }
}
