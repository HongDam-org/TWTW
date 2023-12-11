package com.twtw.backend.domain.plan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDetails {
    private String placeName;
    private String placeUrl;
    private String roadAddressName;
    private Double longitude;
    private Double latitude;
}
