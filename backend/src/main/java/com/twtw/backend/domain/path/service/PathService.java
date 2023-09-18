package com.twtw.backend.domain.path.service;

import com.twtw.backend.domain.path.dto.client.car.SearchCarPathRequest;
import com.twtw.backend.domain.path.dto.client.car.SearchCarPathResponse;
import com.twtw.backend.global.client.MapClient;

import org.springframework.stereotype.Service;

@Service
public class PathService {
    private final MapClient<SearchCarPathRequest, SearchCarPathResponse> client;

    public PathService(MapClient<SearchCarPathRequest, SearchCarPathResponse> client) {
        this.client = client;
    }

    public SearchCarPathResponse searchPath(final SearchCarPathRequest request) {
        return this.client.request(request);
    }
}
