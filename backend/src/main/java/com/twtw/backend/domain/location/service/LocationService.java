package com.twtw.backend.domain.location.service;

import com.twtw.backend.domain.location.dto.request.LocationRequest;
import com.twtw.backend.domain.location.dto.response.LocationResponse;
import com.twtw.backend.domain.location.mapper.LocationMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationMapper locationMapper;

    public LocationResponse addInfo(final LocationRequest locationRequest) {
        return locationMapper.toResponse(locationRequest, LocalDateTime.now());
    }
}
