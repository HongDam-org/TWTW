package com.twtw.backend.domain.path.dto.client.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Summary {
    private ResponsePositionFormat start;
    private ResponsePositionFormat goal;
    private List<ResponsePositionFormat> waypoints;
    private int distance;
    private int duration;
    private Double[][] bbox;
    private int tollFare;
    private int taxiFare;
    private int fuelPrice;
}
