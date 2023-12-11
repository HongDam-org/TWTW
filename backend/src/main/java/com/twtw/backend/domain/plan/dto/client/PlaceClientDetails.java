package com.twtw.backend.domain.plan.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceClientDetails {
    private String placeName;
    private String placeUrl;
    private String roadAddressName;
    private Double x;
    private Double y;
}
