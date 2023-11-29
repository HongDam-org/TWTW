package com.twtw.backend.domain.plan.dto.client;

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
    private String placeUrl;
    private String roadAddressName;
    private Double longitude;
    private Double latitude;
}
