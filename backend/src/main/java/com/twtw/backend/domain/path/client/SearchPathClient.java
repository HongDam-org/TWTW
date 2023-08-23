package com.twtw.backend.domain.path.client;

import com.twtw.backend.domain.path.dto.client.SearchPathRequest;
import com.twtw.backend.domain.path.dto.client.SearchPathResponse;
import com.twtw.backend.global.client.PathClient;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class SearchPathClient implements PathClient<SearchPathRequest, SearchPathResponse> {
    @Qualifier("NaverWebClient")
    private final WebClient webClient;

    public SearchPathClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
