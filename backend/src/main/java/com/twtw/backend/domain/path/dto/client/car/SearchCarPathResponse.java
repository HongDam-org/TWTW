package com.twtw.backend.domain.path.dto.client.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchCarPathResponse {

    private static final SearchCarPathResponse ON_ERROR_RESPONSE =
            new SearchCarPathResponse(0, "Internal Server Error", "", Map.of());

    private int code;
    private String message;
    private String currentDateTime;
    private Map<String, RouteUnitEnt[]> route;

    public static SearchCarPathResponse onError() {
        return ON_ERROR_RESPONSE;
    }
}
