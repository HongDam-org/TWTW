package com.twtw.backend.domain.plan.service;

import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.repository.PlanRepository;
import com.twtw.backend.global.client.MapClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final MapClient<SearchDestinationRequest, SearchDestinationResponse> destinationClient;

    public PlanDestinationResponse searchPlanDestination(final SearchDestinationRequest request) {
        final SearchDestinationResponse response = requestMapClient(request);
        return new PlanDestinationResponse(response.getDocuments(), response.getMeta().getIsEnd());
    }

    private SearchDestinationResponse requestMapClient(final SearchDestinationRequest request) {
        return destinationClient.request(request);
    }
}
