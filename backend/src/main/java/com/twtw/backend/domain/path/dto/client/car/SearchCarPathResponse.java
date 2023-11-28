package com.twtw.backend.domain.path.dto.client.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchCarPathResponse {
    private int code;
    private String message;
    private String currentDateTime;
    private Map<String, RouteUnitEnt[]> route;
}
