package com.twtw.backend.fixture.place;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlaceDetailsFixture {
    ;

    private final String placeName;
    private final String distance;
    private final String placeUrl;
    private final String categoryName;
    private final String addressName;
    private final String roadAddressName;
    private final CategoryGroupCode categoryGroupCode;
    private final Double x;
    private final Double y;
}
