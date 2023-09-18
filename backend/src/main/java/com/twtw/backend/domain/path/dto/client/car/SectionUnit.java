package com.twtw.backend.domain.path.dto.client.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SectionUnit {
    private int pointIndex;
    private int pointCount;
    private int distance;
    private String name;
    private int congestion;
    private int speed;
}
