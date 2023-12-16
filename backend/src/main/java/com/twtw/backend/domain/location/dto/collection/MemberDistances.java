package com.twtw.backend.domain.location.dto.collection;

import lombok.RequiredArgsConstructor;

import org.springframework.data.geo.Point;

import java.util.List;

@RequiredArgsConstructor
public class MemberDistances {

    private static final double DEFAULT_VALUE = 0.0;

    private final List<Point> points;

    public double averageLongitude() {
        return points.stream().mapToDouble(Point::getX).average().orElse(DEFAULT_VALUE);
    }

    public double averageLatitude() {
        return points.stream().mapToDouble(Point::getY).average().orElse(DEFAULT_VALUE);
    }
}
