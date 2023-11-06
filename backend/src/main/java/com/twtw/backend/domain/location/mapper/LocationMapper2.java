package com.twtw.backend.domain.location.mapper;

import com.twtw.backend.domain.location.dto.request.LocationRequest;
import com.twtw.backend.domain.location.dto.response.LocationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper2 {
    LocationResponse toResponse(LocationRequest locationRequest,LocalDateTime now);
}
