package com.twtw.backend.domain.place.mapper;

import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper {

    public Place toEntity(PlaceDetails detail){
        return Place.builder().placeName(detail.getPlaceName())
                .distance(Integer.parseInt(detail.getDistance()))
                .categoryName(detail.getCategoryName())
                .categoryGroupCode(detail.getCategoryGroupCode())
                .placeUrl(detail.getPlaceUrl())
                .addressName(detail.getAddressName())
                .roadAddressName(detail.getRoadAddressName())
                .x(detail.getX())
                .y(detail.getY()).build();
    }
}
