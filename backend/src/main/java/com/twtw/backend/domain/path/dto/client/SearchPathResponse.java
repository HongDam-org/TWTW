package com.twtw.backend.domain.path.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchPathResponse {
    private int code;
    private String message;
    private String currentDateTime;
    private Map<String, RouteUnitEnt[]> route;
}
