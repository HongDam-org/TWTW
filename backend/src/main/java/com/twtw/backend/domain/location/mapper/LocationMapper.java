package com.twtw.backend.domain.location.mapper;

import com.twtw.backend.domain.location.dto.request.LocationRequest;
import com.twtw.backend.domain.location.dto.response.LocationResponse;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class LocationMapper {
    public LocationResponse toResponse(
            final LocationRequest locationRequest, final LocalDateTime now) {
        return LocationResponse.builder()
                .x(locationRequest.getX())
                .y(locationRequest.getY())
                .nickname(locationRequest.getNickname())
                .time(now)
                .build();
    }
}
