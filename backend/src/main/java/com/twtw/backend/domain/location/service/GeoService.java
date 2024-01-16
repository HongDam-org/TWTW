package com.twtw.backend.domain.location.service;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.location.dto.collection.MemberDistances;
import com.twtw.backend.domain.location.dto.request.LocationRequest;
import com.twtw.backend.domain.location.dto.response.AverageCoordinate;
import com.twtw.backend.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoService {

    private static final Distance DEFAULT_DISTANCE = new Distance(0, Metrics.KILOMETERS);
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

        return calculateAverage(collectMemberDistances(planId, group), planId, memberId);
    }

    private MemberDistances collectMemberDistances(final String groupId, final Group group) {
        final List<Point> points =
                redisTemplate.opsForGeo().position(groupId, group.getMemberIds());
        return new MemberDistances(points);
    }

    private AverageCoordinate calculateAverage(
            final MemberDistances memberDistances, final String groupId, final String memberId) {

        final Point averagePoint = memberDistances.getAveragePoint();
        redisTemplate
                .opsForGeo()
                .add(groupId, averagePoint, groupId);

        final Distance distance = distance(groupId, memberId);

        return new AverageCoordinate(averagePoint.getX(), averagePoint.getY(), distance.getValue());
    }

    private Distance distance(final String groupId, final String memberId) {
        final Distance distance =
                redisTemplate.opsForGeo().distance(groupId, memberId, groupId, Metrics.KILOMETERS);

        if (distance == null) {
            return DEFAULT_DISTANCE;
        }
        return distance;
    }
}
