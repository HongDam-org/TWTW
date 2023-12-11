package com.twtw.backend.domain.location.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AverageCoordinate {
    private Double longitude;
    private Double latitude;
    private Double distance;
}
