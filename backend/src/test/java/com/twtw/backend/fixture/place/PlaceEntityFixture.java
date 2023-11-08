package com.twtw.backend.fixture.place;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;
import com.twtw.backend.domain.place.entity.Place;

public enum PlaceEntityFixture {
    FIRST_PLACE(
            "다미네집",
            100,
            "http://everywhereIsMyHome",
            "관광명소",
            CategoryGroupCode.AT4,
            "주소",
            "도로명주소",
            "12.1",
            "21.2"),
    SECOND_PLACE(
            "호진이네집",
            50,
            "http://IDontKnow",
            "숙박",
            CategoryGroupCode.AD5,
            "밥좀주소",
            "도로명주소!",
            "123.123",
            "77.7");

    private final String placeName;
    private final Integer distance;
    private final String placeUrl;
    private final String categoryName;
    private final CategoryGroupCode categoryGroupCode;
    private final String addressName;
    private final String roadAddressName;
    private final String x;
    private final String y;

    PlaceEntityFixture(
            final String placeName,
            final Integer distance,
            final String placeUrl,
            final String categoryName,
            final CategoryGroupCode categoryGroupCode,
            final String addressName,
            final String roadAddressName,
            final String x,
            final String y) {
        this.placeName = placeName;
        this.distance = distance;
        this.placeUrl = placeUrl;
        this.categoryName = categoryName;
        this.categoryGroupCode = categoryGroupCode;
        this.addressName = addressName;
        this.roadAddressName = roadAddressName;
        this.x = x;
        this.y = y;
    }

    public Place toEntity() {
        return new Place(
                placeName,
                distance,
                placeUrl,
                categoryName,
                categoryGroupCode,
                addressName,
                roadAddressName,
                x,
                y);
    }
}
