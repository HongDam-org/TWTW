package com.twtw.backend.domain.location.dto.collection;

import lombok.RequiredArgsConstructor;

import org.springframework.data.geo.Point;

import java.util.List;

@RequiredArgsConstructor
public class MemberDistances {

    private static final double DEFAULT_VALUE = 0.0;
    private static final Point DEFAULT_POINT = new Point(DEFAULT_VALUE, DEFAULT_VALUE);

    private final List<Point> points;

    private double averageLongitude() {
        if (points == null || points.isEmpty()) {
            return DEFAULT_VALUE;
        }
        return points.stream().mapToDouble(Point::getX).average().orElse(DEFAULT_VALUE);
    }

    private double averageLatitude() {
        if (points == null || points.isEmpty()) {
            return DEFAULT_VALUE;
        }
        return points.stream().mapToDouble(Point::getY).average().orElse(DEFAULT_VALUE);
    }

    public Point getAveragePoint() {
        final double averageLongitude = averageLongitude();
        final double averageLatitude = averageLatitude();

        if (averageLongitude == DEFAULT_VALUE || averageLatitude == DEFAULT_VALUE) {
            return DEFAULT_POINT;
        }
        return new Point(averageLongitude, averageLatitude);
    }
}
