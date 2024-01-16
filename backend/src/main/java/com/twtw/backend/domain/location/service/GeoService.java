package com.twtw.backend.domain.location.service;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.location.dto.collection.MemberDistances;
import com.twtw.backend.domain.location.dto.request.LocationRequest;
import com.twtw.backend.domain.location.dto.response.AverageCoordinate;
import com.twtw.backend.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeoService {

    private static final int CURRENT_LOCATION_INDEX = 0;
    private final RedisTemplate<String, String> redisTemplate;

    public AverageCoordinate saveLocation(
            final Group group, final Member member, final LocationRequest locationRequest) {
        final String planId = group.getId().toString();
        final String memberId = member.getId().toString();

        redisTemplate
                .opsForGeo()
                .add(
                        planId,
                        new Point(locationRequest.getLongitude(), locationRequest.getLatitude()),
                        memberId);

        return calculateAverage(collectMemberDistances(planId), planId, memberId);
    }

    private MemberDistances collectMemberDistances(final String planId) {
        return redisTemplate.opsForSet().members(planId).stream()
                .map(
                        member ->
                                redisTemplate
                                        .opsForGeo()
                                        .position(planId, member)
                                        .get(CURRENT_LOCATION_INDEX))
                .collect(Collectors.collectingAndThen(Collectors.toList(), MemberDistances::new));
    }

    private AverageCoordinate calculateAverage(
            final MemberDistances memberDistances, final String planId, final String memberId) {

        final double averageLongitude = memberDistances.averageLongitude();
        final double averageLatitude = memberDistances.averageLatitude();

        redisTemplate.opsForGeo().add(planId, new Point(averageLongitude, averageLatitude), planId);

        final Double distance = distance(planId, memberId);

        return new AverageCoordinate(averageLongitude, averageLatitude, distance);
    }

    private double distance(final String planId, final String memberId) {
        return redisTemplate
                .opsForGeo()
                .distance(planId, memberId, planId, Metrics.KILOMETERS)
                .getValue();
    }
}
