package com.twtw.backend.domain.location.service;

import com.twtw.backend.domain.location.dto.request.LocationRequest;
import com.twtw.backend.domain.location.dto.response.AverageCoordinate;
import com.twtw.backend.domain.location.dto.response.LocationResponse;
import com.twtw.backend.domain.location.mapper.LocationMapper;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.service.MemberService;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.domain.plan.service.PlanService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationMapper locationMapper;
    private final MemberService memberService;
    private final PlanService planService;
    private final GeoService geoService;

    @Transactional
    public LocationResponse addInfo(final UUID planId, final LocationRequest locationRequest) {
        final Member member = memberService.getMemberById(locationRequest.getMemberId());
        final Plan plan = planService.getPlanEntity(planId);

        plan.updateMemberLocation(
                member, locationRequest.getLongitude(), locationRequest.getLatitude());

        final AverageCoordinate averageCoordinate =
                geoService.saveLocation(plan, member, locationRequest);

        return locationMapper.toResponse(locationRequest, averageCoordinate, LocalDateTime.now());
    }
}
