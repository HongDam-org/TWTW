package com.twtw.backend.domain.path.service;

import com.twtw.backend.domain.path.dto.client.SearchPathRequest;
import com.twtw.backend.domain.path.dto.client.SearchPathResponse;

import com.twtw.backend.global.client.MapClient;
import org.springframework.stereotype.Service;

@Service
public class PathService {
    private final MapClient<SearchPathRequest, SearchPathResponse> client;

    public PathService(MapClient<SearchPathRequest, SearchPathResponse> client) {
        this.client = client;
    }

    public SearchPathResponse searchPath(final SearchPathRequest request) {
        return this.client.request(request);
    }
}
