package com.twtw.backend.domain.plan.mapper;

import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.entity.Plan;

import org.springframework.stereotype.Component;

@Component
public class PlanMapper {
    public PlanResponse toPlanResponse(Plan plan) {
        return new PlanResponse(plan.getId(), plan.getGroup().getId());
    }
}
