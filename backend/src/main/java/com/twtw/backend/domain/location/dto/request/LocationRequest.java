package com.twtw.backend.domain.location.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {
    private String nickname;
    private Double longitude;
    private Double latitude;
}
