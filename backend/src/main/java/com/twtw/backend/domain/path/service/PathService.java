package com.twtw.backend.domain.path.service;

import com.twtw.backend.domain.path.dto.client.car.SearchCarPathRequest;
import com.twtw.backend.domain.path.dto.client.car.SearchCarPathResponse;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathRequest;
import com.twtw.backend.domain.path.dto.client.ped.SearchPedPathResponse;
import com.twtw.backend.global.client.MapClient;

import org.springframework.cache.annotation.Cacheable;
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

    @Cacheable(
            value = "carPath",
            key = "'searchCarPath'.concat(#request)",
            cacheManager = "cacheManager",
            unless = "#result.code != 0")
    public SearchCarPathResponse searchCarPath(final SearchCarPathRequest request) {
        return carPathClient.request(request);
    }

    @Cacheable(
            value = "pedPath",
            key = "'searchPedPath'.concat(#request)",
            cacheManager = "cacheManager",
            unless = "#result.features.size() <= 0")
    public SearchPedPathResponse searchPedPath(final SearchPedPathRequest request) {
        return pedPathClient.request(request);
    }
}
