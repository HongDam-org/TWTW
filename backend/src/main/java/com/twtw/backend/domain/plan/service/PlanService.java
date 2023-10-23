package com.twtw.backend.domain.plan.service;

import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.service.GroupService;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.domain.member.service.MemberService;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.place.service.PlaceService;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationResponse;
import com.twtw.backend.domain.plan.dto.request.PlanMemberRequest;
import com.twtw.backend.domain.plan.dto.request.SavePlanRequest;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.domain.plan.mapper.PlanMapper;
import com.twtw.backend.domain.plan.repository.PlanRepository;
import com.twtw.backend.global.client.MapClient;

import com.twtw.backend.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final GroupService groupService;

    private final MemberService memberService;

    private final PlaceService placeService;
    private final AuthService authService;
    private final PlanMapper planMapper;

    private final MapClient<SearchDestinationRequest, SearchDestinationResponse> destinationClient;

    public PlanDestinationResponse searchPlanDestination(final SearchDestinationRequest request) {
        final SearchDestinationResponse response = requestMapClient(request);
        return new PlanDestinationResponse(response.getDocuments(), response.getMeta().getIsEnd());
    }

    private SearchDestinationResponse requestMapClient(final SearchDestinationRequest request) {
        return destinationClient.request(request);
    }

    @Transactional
    public PlanResponse savePlan(final SavePlanRequest request) {
        Member member = authService.getMemberByJwt();
        Group group = groupService.getGroupEntity(request.getGroupId());
        Place place = placeService.getEntityByDetail(request.getPlaceDetails());
        Plan plan = new Plan(member, place, group);

        return planMapper.toPlanResponse(planRepository.save(plan));
    }

    @Transactional
    public PlanResponse joinPlan(PlanMemberRequest request){
        Member member = authService.getMemberByJwt();
        Plan plan = getPlanEntity(request.getPlanId());
        plan.addMember(member);

        return planMapper.toPlanResponse(plan);
    }

    @Transactional
    public void outPlan(PlanMemberRequest request){
        Member member = authService.getMemberByJwt();
        Plan plan = getPlanEntity(request.getPlanId());
        plan.getPlanMembers().remove(member);
    }

    @Transactional
    public PlanInfoResponse getPlanById(UUID id){
        Plan plan = getPlanEntity(id);

        GroupInfoResponse groupInfo = groupService.getGroupInfoResponse(plan.getGroup());
        PlaceDetails placeDetails = placeService.getPlaceDetails(plan.getPlace());

        List<MemberResponse> memberResponse = plan.getPlanMembers().stream()
                .map(x -> memberService.getResponseByMember(x.getMember()))
                .collect(Collectors.toList());

        return new PlanInfoResponse(
                plan.getId(),
                plan.getPlace().getId(),
                placeDetails,
                groupInfo,
                memberResponse
        );
    }

    @Transactional
    public void deletePlan(UUID id){
        planRepository.deleteById(id);
    }


    private Plan getPlanEntity(UUID id){
        return planRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
