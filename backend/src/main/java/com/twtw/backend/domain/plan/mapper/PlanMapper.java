package com.twtw.backend.domain.plan.mapper;

import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.entity.Plan;

import com.twtw.backend.domain.plan.entity.PlanMember;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class PlanMapper {
    public PlanResponse toPlanResponse(Plan plan) {
        return new PlanResponse(plan.getId(), plan.getGroup().getId());
    }

}
