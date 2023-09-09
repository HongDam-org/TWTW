package com.twtw.backend.domain.path.service;

import com.twtw.backend.domain.path.dto.client.SearchPathRequest;
import com.twtw.backend.domain.path.dto.client.SearchPathResponse;
import com.twtw.backend.global.client.PathClient;

import org.springframework.stereotype.Service;

@Service
public class PathService {
    private final PathClient<SearchPathRequest, SearchPathResponse> client;

    public PathService(PathClient<SearchPathRequest, SearchPathResponse> client) {
        this.client = client;
    }

    public SearchPathResponse searchPath(final SearchPathRequest request) {
        return this.client.request(request);
    }
}
