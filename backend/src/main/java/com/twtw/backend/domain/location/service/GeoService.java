package com.twtw.backend.domain.location.service;

import com.twtw.backend.domain.location.dto.request.LocationRequest;
import com.twtw.backend.domain.location.dto.response.AverageCoordinate;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.plan.entity.Plan;

import lombok.RequiredArgsConstructor;

import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeoService {

    private final RedisTemplate<String, String> redisTemplate;

    public AverageCoordinate saveLocation(
            final Plan plan, final Member member, final LocationRequest locationRequest) {
        final String planId = plan.getId().toString();

        redisTemplate
                .opsForGeo()
                .add(
                        planId,
                        new Point(locationRequest.getLongitude(), locationRequest.getLatitude()),
                        member.getId().toString());

        return calculate(planId, locationRequest);
    }

    private AverageCoordinate calculate(
            final String planId, final LocationRequest locationRequest) {
        final Double userLongitude = locationRequest.getLongitude();
        final Double userLatitude = locationRequest.getLatitude();

        GeoResults<GeoLocation<String>> geoResults =
                redisTemplate
                        .opsForGeo()
                        .radius(
                                planId,
                                new Circle(
                                        new Point(userLongitude, userLatitude),
                                        new Distance(0, Metrics.KILOMETERS)));

        if (geoResults == null) {
            return new AverageCoordinate();
        }

        final List<GeoResult<GeoLocation<String>>> content = geoResults.getContent();

        if (content.isEmpty()) {
            return new AverageCoordinate();
        }

        return calculateAverage(content, userLatitude, userLongitude);
    }

    private AverageCoordinate calculateAverage(
            final List<GeoResult<GeoLocation<String>>> content,
            final Double userLatitude,
            final Double userLongitude) {

        double totalLatitude = 0.0;
        double totalLongitude = 0.0;

        for (GeoResult<GeoLocation<String>> geoResult : content) {
            Point point = geoResult.getContent().getPoint();
            totalLatitude += point.getY();
            totalLongitude += point.getX();
        }

        final int size = content.size();
        double avgLatitude = totalLatitude / size;
        double avgLongitude = totalLongitude / size;

        final Double distance = distance(userLatitude, userLongitude, avgLatitude, avgLongitude);

        return new AverageCoordinate(avgLongitude, avgLatitude, distance);
    }

    private Double distance(
            final Double lat1, final Double lon1, final Double lat2, final Double lon2) {
        int radius = 6371;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a =
                Math.sin(dLat / 2) * Math.sin(dLat / 2)
                        + Math.cos(Math.toRadians(lat1))
                                * Math.cos(Math.toRadians(lat2))
                                * Math.sin(dLon / 2)
                                * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return radius * c;
    }
}
