package com.twtw.backend.domain.plan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlanResponse {
    private UUID planId;
    private UUID groupId;
}
