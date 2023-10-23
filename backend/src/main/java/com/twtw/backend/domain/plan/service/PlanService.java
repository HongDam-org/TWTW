package com.twtw.backend.domain.plan.service;

import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.mapper.GroupMapper;
import com.twtw.backend.domain.group.service.GroupService;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.mapper.MemberMapper;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.place.mapper.PlaceMapper;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationResponse;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final GroupService groupService;
    private final AuthService authService;
    private final PlaceMapper placeMapper;
    private final PlanMapper planMapper;
    private final GroupMapper groupMapper;
    private final MemberMapper memberMapper;

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
        Place place = placeMapper.toEntity(request.getPlaceDetails());
        Plan plan = new Plan(member, place, group);

        return planMapper.toPlanResponse(planRepository.save(plan));
    }

    @Transactional
    public PlanResponse joinPlan(UUID id){
        Member member = authService.getMemberByJwt();
        Plan plan = planRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        plan.addMember(member);

        return planMapper.toPlanResponse(plan);
    }

    @Transactional
    public void outPlan(UUID id){
        Member member = authService.getMemberByJwt();
        Plan plan = planRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        plan.getPlanMembers().remove(member);
    }

    @Transactional
    public PlanInfoResponse getPlanById(UUID id){
        Plan plan = planRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        GroupInfoResponse groupInfo = groupMapper.toGroupInfo(plan.getGroup());
        PlaceDetails placeDetails = placeMapper.toPlaceResponse(plan.getPlace());

        List<MemberResponse> memberResponse = new ArrayList<>();
        plan.getPlanMembers().forEach(
                x -> memberResponse.add(memberMapper.toMemberResponse(x.getMember()))
        );

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
}
