package com.twtw.backend.domain.location.mapper;

import com.twtw.backend.domain.location.dto.request.LocationRequest;
import com.twtw.backend.domain.location.dto.response.AverageCoordinate;
import com.twtw.backend.domain.location.dto.response.LocationResponse;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {
    LocationResponse toResponse(LocationRequest locationRequest, AverageCoordinate averageCoordinate, LocalDateTime time);
}
