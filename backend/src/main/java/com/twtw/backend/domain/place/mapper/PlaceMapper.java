package com.twtw.backend.domain.place.mapper;

import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlaceMapper {
    @Mapping(target ="distance",source = "distance",qualifiedByName = "convertInteger")
    Place toEntity(PlaceDetails detail);
    @Mapping(target ="distance",source = "distance",qualifiedByName = "convertString")
    PlaceDetails toPlaceResponse(Place place);

    @Named("convertInteger")
    default Integer convertInteger(String distance){
        return Integer.parseInt(distance);
    }

    @Named("convertString")
    default String convertString(Integer distance){
        return String.valueOf(distance);
    }
}
