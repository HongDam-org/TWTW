package com.twtw.backend.fixture.place;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;

import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlaceDetailsFixture {
    FIRST_PLACE("스타벅스", 30, "http://someUrlToPlaceDetails", "카페", "주소", "도로명주소", CategoryGroupCode.CE7, 123.123, 321.321),
    SECOND_PLACE("별다방", 20, "http://someUrlToPlaceInMap", "숙박", "밥좀주소", "도로명좀주소", CategoryGroupCode.AD5, 345.543, 543.345);

    private final String placeName;
    private final Integer distance;
    private final String placeUrl;
    private final String categoryName;
    private final String addressName;
    private final String roadAddressName;
    private final CategoryGroupCode categoryGroupCode;
    private final Double x;
    private final Double y;

    public PlaceDetails toPlaceDetails() {
        return new PlaceDetails(placeName, distance, placeUrl, categoryName, addressName, roadAddressName, categoryGroupCode, x, y);
    }
}
