package com.twtw.backend.domain.place.mapper;

import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaceMapper {
    @Mapping(target = "distance")
    Place toEntity(PlaceDetails detail);

    @Mapping(target = "distance")
    PlaceDetails toPlaceResponse(Place place);
}
