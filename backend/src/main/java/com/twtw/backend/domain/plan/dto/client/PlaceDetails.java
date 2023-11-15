package com.twtw.backend.domain.plan.dto.client;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDetails {
    private String placeName;
    private Integer distance;
    private String placeUrl;
    private String categoryName;
    private String addressName;
    private String roadAddressName;
    private CategoryGroupCode categoryGroupCode;
    private Double x;
    private Double y;
}
