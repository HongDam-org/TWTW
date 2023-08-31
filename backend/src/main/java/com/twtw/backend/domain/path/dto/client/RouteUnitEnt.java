package com.twtw.backend.domain.path.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RouteUnitEnt {
    Summary summary;
    List<Double[]> path;
}
