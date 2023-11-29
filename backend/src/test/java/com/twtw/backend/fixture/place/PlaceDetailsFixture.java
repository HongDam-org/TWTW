package com.twtw.backend.fixture.place;

import com.twtw.backend.domain.plan.dto.client.PlaceDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlaceDetailsFixture {
    FIRST_PLACE("스타벅스", "http://someUrlToPlaceDetails", "도로명주소", 123.123, 321.321),
    SECOND_PLACE("별다방", "http://someUrlToPlaceInMap", "도로명좀주소", 345.543, 543.345);

    private final String placeName;
    private final String placeUrl;
    private final String roadAddressName;
    private final Double longitude;
    private final Double latitude;

    public PlaceDetails toPlaceDetails() {
        return new PlaceDetails(placeName, placeUrl, roadAddressName, longitude, latitude);
    }
}
