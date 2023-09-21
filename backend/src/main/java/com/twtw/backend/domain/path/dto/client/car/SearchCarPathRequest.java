package com.twtw.backend.domain.path.dto.client.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchCarPathRequest {
    private String start;
    private String end;
    private String way;
    private SearchPathOption option;
    private SearchPathFuel fuel;
    private int car;
}