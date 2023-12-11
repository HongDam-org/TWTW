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
import com.twtw.backend.domain.plan.dto.request.UpdatePlanRequest;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.domain.plan.mapper.PlanMapper;
import com.twtw.backend.domain.plan.repository.PlanRepository;
import com.twtw.backend.global.client.MapClient;
import com.twtw.backend.global.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;
    private final GroupService groupService;

    private final MemberService memberService;

    private final PlaceService placeService;
    private final AuthService authService;
    private final PlanMapper planMapper;

    private final MapClient<SearchDestinationRequest, SearchDestinationResponse> destinationClient;

    @Cacheable(
            value = "planDestination",
            key = "{#request}",
            cacheManager = "cacheManager",
            unless = "#result.results.size() <= 0")
    public PlanDestinationResponse searchPlanDestination(final SearchDestinationRequest request) {

        final SearchDestinationResponse response = requestMapClient(request);
        return planMapper.toPlanDestinationResponse(response);
    }

    private SearchDestinationResponse requestMapClient(final SearchDestinationRequest request) {
        final SearchDestinationResponse result = destinationClient.request(request);
        final List<PlaceDetails> documents = result.getDocuments();

        if (hasNoSearchData(documents)) {
            return destinationClient.request(request.toNoDirectionRequest());
        }
        return result;
    }

    private boolean hasNoSearchData(final List<PlaceDetails> documents) {
        return documents == null || documents.isEmpty();
    }

    public PlanResponse savePlan(final SavePlanRequest request) {
        Member member = authService.getMemberByJwt();
        Group group = groupService.getGroupEntity(request.getGroupId());
        Place place = placeService.getEntityByDetail(request.getPlaceDetails());
        Plan plan = new Plan(member, place, group);

        return planMapper.toPlanResponse(planRepository.save(plan));
    }

    public PlanResponse joinPlan(PlanMemberRequest request) {
        Member member = authService.getMemberByJwt();
        Plan plan = getPlanEntity(request.getPlanId());
        plan.addMember(member);

        return planMapper.toPlanResponse(plan);
    }

    public void outPlan(PlanMemberRequest request) {
        Member member = authService.getMemberByJwt();
        Plan plan = getPlanEntity(request.getPlanId());
        plan.deleteMember(member);
    }

    public PlanInfoResponse getPlanById(UUID id) {
        Plan plan = getPlanEntity(id);

        GroupInfoResponse groupInfo = groupService.getGroupInfoResponse(plan.getGroup());
        PlaceDetails placeDetails = placeService.getPlaceDetails(plan.getPlace());

        List<MemberResponse> memberResponse = toMemberResponse(plan);

        return planMapper.toPlanInfoResponse(plan, placeDetails, groupInfo, memberResponse);
    }

    public void deletePlan(UUID id) {
        planRepository.deleteById(id);
    }

    private List<MemberResponse> toMemberResponse(Plan plan) {
        return memberService.getMemberResponses(plan);
    }

    public Plan getPlanEntity(UUID id) {
        return planRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<PlanInfoResponse> getPlans() {
        final Member member = authService.getMemberByJwt();
        final List<Plan> plans = planRepository.findAllByMember(member);
        return planMapper.toPlanInfoResponses(plans);
    }

    public void updatePlan(final UpdatePlanRequest updatePlanRequest) {
        final Plan plan = getPlanEntity(updatePlanRequest.getPlanId());

        plan.updatePlace(
                updatePlanRequest.getPlaceName(),
                updatePlanRequest.getPlaceUrl(),
                updatePlanRequest.getCategoryGroupCode(),
                updatePlanRequest.getRoadAddressName(),
                updatePlanRequest.getLongitude(),
                updatePlanRequest.getLatitude());
    }
}
