package com.twtw.backend.fixture.place;

import com.twtw.backend.domain.place.entity.Place;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlaceEntityFixture {
    FIRST_PLACE("다미네집", "http://everywhereIsMyHome", "도로명주소", 12.1, 21.2),
    SECOND_PLACE("호진이네집", "http://IDontKnow", "도로명주소!", 123.123, 77.7);

    private final String placeName;
    private final String placeUrl;
    private final String roadAddressName;
    private final Double longitude;
    private final Double latitude;

    public Place toEntity() {
        return new Place(placeName, placeUrl, roadAddressName, longitude, latitude);
    }
}
