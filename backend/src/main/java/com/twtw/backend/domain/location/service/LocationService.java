package com.twtw.backend.domain.location.service;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.service.GroupService;
import com.twtw.backend.domain.location.dto.request.LocationRequest;
import com.twtw.backend.domain.location.dto.response.AverageCoordinate;
import com.twtw.backend.domain.location.dto.response.LocationResponse;
import com.twtw.backend.domain.location.mapper.LocationMapper;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.service.MemberService;

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
    private final GroupService groupService;
    private final GeoService geoService;

    @Transactional
    public LocationResponse addInfo(final UUID groupId, final LocationRequest locationRequest) {
        final Member member = memberService.getMemberById(locationRequest.getMemberId());
        final Group group = groupService.getGroupEntity(groupId);
        group.shareOnce(member);

        group.updateMemberLocation(
                member, locationRequest.getLongitude(), locationRequest.getLatitude());

        final AverageCoordinate averageCoordinate =
                geoService.saveLocation(group, member, locationRequest);

        return locationMapper.toResponse(locationRequest, averageCoordinate, LocalDateTime.now());
    }
}
