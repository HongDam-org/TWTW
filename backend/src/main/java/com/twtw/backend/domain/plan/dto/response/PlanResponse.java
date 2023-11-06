package com.twtw.backend.domain.plan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class PlanResponse {
    private UUID planId;
    private UUID groupId;
}
