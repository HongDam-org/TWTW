package com.twtw.backend.domain.path.service;

import com.twtw.backend.domain.path.dto.client.car.SearchCarPathRequest;
import com.twtw.backend.domain.path.dto.client.car.SearchCarPathResponse;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathRequest;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathResponse;
import com.twtw.backend.global.client.MapClient;

import org.springframework.stereotype.Service;

@Service
public class PathService {
    private final MapClient<SearchCarPathRequest, SearchCarPathResponse> carPathClient;
    private final MapClient<SearchPedPathRequest, SearchPedPathResponse> pedPathClient;

    public PathService(
            MapClient<SearchCarPathRequest, SearchCarPathResponse> carPathClient,
            MapClient<SearchPedPathRequest, SearchPedPathResponse> pedPathClient) {
        this.carPathClient = carPathClient;
        this.pedPathClient = pedPathClient;
    }

    public SearchCarPathResponse searchCarPath(final SearchCarPathRequest request) {
        return this.carPathClient.request(request);
    }

    public SearchPedPathResponse searchPedPath(final SearchPedPathRequest request) {
        return this.pedPathClient.request(request);
    }
}
