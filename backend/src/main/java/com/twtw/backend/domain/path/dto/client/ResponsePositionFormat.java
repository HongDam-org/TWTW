package com.twtw.backend.domain.path.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePositionFormat {
    Double[][] location;
    int dir;
    int distance;
    int duration;
    int pointIndex;
}
