package com.twtw.backend.domain.plan.dto.response;

import com.twtw.backend.domain.plan.dto.client.PlaceDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PlanDestinationResponse {
    private List<PlaceDetails> results;
    private Boolean isLast;
}
