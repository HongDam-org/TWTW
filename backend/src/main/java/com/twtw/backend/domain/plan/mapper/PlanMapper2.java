package com.twtw.backend.domain.plan.mapper;

import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.entity.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlanMapper2 {
    @Mapping(target = "planId", source ="id")
    @Mapping(target = "groupId", source = "group.id")
    PlanResponse toPlanResponse(Plan plan);
}
