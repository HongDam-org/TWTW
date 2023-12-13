package com.twtw.backend.domain.plan.mapper;

import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.plan.dto.client.PlaceClientDetails;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlaceDetails;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.entity.Plan;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

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
    PlanInfoResponse toPlanInfoResponse(
            Plan plan,
            PlaceClientDetails placeDetails,
            GroupInfoResponse groupInfoResponse,
            List<MemberResponse> memberResponses);

    @IterableMapping(qualifiedByName = "toPlaceDetail")
    List<PlaceDetails> toPlaceDetails(List<PlaceClientDetails> placeClientDetails);

    @Named("toPlaceDetail")
    @Mapping(target = "longitude", source = "x")
    @Mapping(target = "latitude", source = "y")
    PlaceDetails toPlaceDetail(PlaceClientDetails placeClientDetails);
}
