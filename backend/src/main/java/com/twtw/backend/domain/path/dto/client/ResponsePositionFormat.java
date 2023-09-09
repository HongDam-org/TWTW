package com.twtw.backend.domain.path.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePositionFormat {
    private Double[][] location;
    private int dir;
    private int distance;
    private int duration;
    private int pointIndex;
}
