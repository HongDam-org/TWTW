package com.twtw.backend.domain.plan.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PlanDestinationResponse {
    private List<PlaceDetails> results;
    private Boolean isLast;
}
