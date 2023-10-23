package com.twtw.backend.domain.place.mapper;

import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;

import org.springframework.stereotype.Component;

@Component
public class PlaceMapper {

    public Place toEntity(PlaceDetails detail) {
        return Place.builder()
                .placeName(detail.getPlaceName())
                .distance(Integer.parseInt(detail.getDistance()))
                .categoryName(detail.getCategoryName())
                .categoryGroupCode(detail.getCategoryGroupCode())
                .placeUrl(detail.getPlaceUrl())
                .addressName(detail.getAddressName())
                .roadAddressName(detail.getRoadAddressName())
                .x(detail.getX())
                .y(detail.getY())
                .build();
    }

    public PlaceDetails toPlaceResponse(Place place) {
        return new PlaceDetails(
                place.getPlaceName(),
                String.valueOf(place.getDistance()),
                place.getPlaceUrl(),
                place.getCategoryName(),
                place.getAddress().getAddressName(),
                place.getAddress().getRoadAddressName(),
                place.getCategoryGroupCode(),
                String.valueOf(place.getCoordinate().getX()),
                String.valueOf(place.getCoordinate().getY()));
    }
}
