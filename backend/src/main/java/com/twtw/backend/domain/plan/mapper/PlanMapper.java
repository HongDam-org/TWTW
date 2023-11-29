package com.twtw.backend.domain.plan.mapper;

import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.entity.Plan;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlanMapper {
    @Mapping(target = "planId", source = "id")
    @Mapping(target = "groupId", source = "group.id")
    PlanResponse toPlanResponse(Plan plan);

    @Mapping(target = "results", source = "response.documents")
    @Mapping(target = "isLast", source = "response.meta.isEnd")
    PlanDestinationResponse toPlanDestinationResponse(SearchDestinationResponse response);

    @Mapping(target = "planId", source = "plan.id")
    @Mapping(target = "placeId", source = "plan.place.id")
    @Mapping(target = "planMakerId", source = "plan.planMakerId")
    @Mapping(target = "groupInfo", source = "groupInfoResponse")
    @Mapping(target = "members", source = "memberResponses")
    PlanInfoResponse toPlanInfoResponse(Plan plan, PlaceDetails placeDetails, GroupInfoResponse groupInfoResponse, List<MemberResponse> memberResponses);

    @IterableMapping(elementTargetType = PlanInfoResponse.class)
    List<PlanInfoResponse> toPlanInfoResponses(List<Plan> plans);
}
