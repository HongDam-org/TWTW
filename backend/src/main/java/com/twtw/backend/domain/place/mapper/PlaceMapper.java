package com.twtw.backend.domain.place.mapper;

import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.dto.response.PlaceDetails;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaceMapper {

    Place toEntity(PlaceDetails detail);

    @Mapping(target = "longitude", source = "coordinate.longitude")
    @Mapping(target = "latitude", source = "coordinate.latitude")
    PlaceDetails toPlaceResponse(Place place);
}
