package com.twtw.backend.domain.plan.service;

import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.service.GroupService;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.domain.member.service.MemberService;
import com.twtw.backend.domain.notification.dto.NotificationRequest;
import com.twtw.backend.domain.notification.messagequeue.FcmProducer;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.place.service.PlaceService;
import com.twtw.backend.domain.plan.dto.client.PlaceClientDetails;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationRequest;
import com.twtw.backend.domain.plan.dto.client.SearchDestinationResponse;
import com.twtw.backend.domain.plan.dto.request.PlanMemberRequest;
import com.twtw.backend.domain.plan.dto.request.SavePlanRequest;
import com.twtw.backend.domain.plan.dto.request.UpdatePlanDayRequest;
import com.twtw.backend.domain.plan.dto.request.UpdatePlanRequest;
import com.twtw.backend.domain.plan.dto.response.PlaceDetails;
import com.twtw.backend.domain.plan.dto.response.PlanDestinationResponse;
import com.twtw.backend.domain.plan.dto.response.PlanInfoResponse;
import com.twtw.backend.domain.plan.dto.response.PlanResponse;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.domain.plan.mapper.PlanMapper;
import com.twtw.backend.domain.plan.repository.PlanRepository;
import com.twtw.backend.global.client.MapClient;
import com.twtw.backend.global.constant.NotificationBody;
import com.twtw.backend.global.constant.NotificationTitle;
import com.twtw.backend.global.exception.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PlanService {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final PlanRepository planRepository;
    private final GroupService groupService;
    private final MemberService memberService;
    private final PlaceService placeService;
    private final AuthService authService;
    private final PlanMapper planMapper;
    private final FcmProducer fcmProducer;
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
        final List<PlaceClientDetails> documents = result.getDocuments();

        if (hasNoSearchData(documents)) {
            return destinationClient.request(request.toNoDirectionRequest());
        }
        return result;
    }

    private boolean hasNoSearchData(final List<PlaceClientDetails> documents) {
        return documents == null || documents.isEmpty();
    }

    @Transactional
    public PlanResponse savePlan(final SavePlanRequest request) {
        Member member = authService.getMemberByJwt();
        Group group = groupService.getGroupEntity(request.getGroupId());
        Place place = placeService.getEntityByDetail(request.getPlaceDetails());

        final Plan plan =
                planRepository.save(
                        planMapper.toEntity(
                                request.getName(), member, place, group, request.getPlanDay()));
        plan.addMembers(memberService.getMembersByIds(request.getMemberIds()));

        return planMapper.toPlanResponse(plan);
    }

    @Transactional
    public PlanResponse invitePlan(PlanMemberRequest request) {
        Member member = authService.getMemberByJwt();
        Plan plan = getPlanEntity(request.getPlanId());
        plan.addMember(member);

        sendRequestNotification(member.getDeviceTokenValue(), plan.getName(), plan.getId());

        return planMapper.toPlanResponse(plan);
    }

    private void sendRequestNotification(
            final String deviceToken, final String planName, final UUID id) {
        fcmProducer.sendNotification(
                new NotificationRequest(
                        deviceToken,
                        NotificationTitle.PLAN_REQUEST_TITLE.getName(),
                        NotificationBody.PLAN_REQUEST_BODY.toNotificationBody(planName),
                        id));
    }

    public void outPlan(PlanMemberRequest request) {
        Member member = authService.getMemberByJwt();
        Plan plan = getPlanEntity(request.getPlanId());
        plan.deleteMember(member);
    }

    @Transactional(readOnly = true)
    public PlanInfoResponse getPlanById(UUID id) {
        Plan plan = getPlanEntity(id);

        return getPlanInfoResponse(plan);
    }

    private PlanInfoResponse getPlanInfoResponse(final Plan plan) {
        GroupInfoResponse groupInfo = groupService.getGroupInfoResponse(plan.getGroup());
        PlaceDetails placeDetails = placeService.getPlaceDetails(plan.getPlace());
        List<MemberResponse> notJoinedMembers =
                memberService.getResponsesByMembers(plan.getNotJoinedMembers());
        String planDay = plan.getPlanDay().format(DATE_TIME_FORMATTER);
        List<MemberResponse> memberResponses = memberService.getMemberResponses(plan);

        return planMapper.toPlanInfoResponse(
                plan, placeDetails, planDay, groupInfo, memberResponses, notJoinedMembers);
    }

    public void deletePlan(UUID id) {
        planRepository.deleteById(id);
    }

    public Plan getPlanEntity(UUID id) {
        return planRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<PlanInfoResponse> getPlans() {
        final Member member = authService.getMemberByJwt();
        final List<Plan> plans = planRepository.findAllByMember(member);
        return plans.stream().map(this::getPlanInfoResponse).toList();
    }

    @Transactional
    public void updatePlan(final UpdatePlanRequest updatePlanRequest) {
        final Plan plan = getPlanEntity(updatePlanRequest.getPlanId());

        final String placeName = updatePlanRequest.getPlaceName();
        plan.updatePlace(
                updatePlanRequest.getName(),
                updatePlanRequest.getPlanDay(),
                placeName,
                updatePlanRequest.getPlaceUrl(),
                updatePlanRequest.getCategoryGroupCode(),
                updatePlanRequest.getRoadAddressName(),
                updatePlanRequest.getLongitude(),
                updatePlanRequest.getLatitude());

        plan.addMembers(memberService.getMembersByIds(updatePlanRequest.getMemberIds()));

        plan.getPlanMembers()
                .forEach(
                        planMember ->
                                sendDestinationNotification(
                                        planMember.getDeviceTokenValue(), placeName, plan.getId()));
    }

    private void sendDestinationNotification(
            final String deviceToken, final String destinationName, final UUID id) {
        fcmProducer.sendNotification(
                new NotificationRequest(
                        deviceToken,
                        NotificationTitle.DESTINATION_CHANGE_TITLE.getName(),
                        NotificationBody.DESTINATION_CHANGE_BODY.toNotificationBody(
                                destinationName),
                        id));
    }

    @Transactional
    public void updatePlanDay(final UpdatePlanDayRequest request) {
        final Plan plan = getPlanEntity(request.getPlanId());
        plan.updatePlanDay(request.getChangeDay());
    }

    @Transactional
    public void joinPlan(final PlanMemberRequest request) {
        Member member = authService.getMemberByJwt();
        Plan plan = getPlanEntity(request.getPlanId());
        plan.acceptInvite(member);
    }

    @Transactional
    public void deleteInvite(final PlanMemberRequest request) {
        Member member = authService.getMemberByJwt();
        Plan plan = getPlanEntity(request.getPlanId());
        plan.deleteInvite(member);
    }
}
