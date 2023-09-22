package com.twtw.backend.domain.place.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurroundPlaceRequest {
    private String x;
    private String y;
    private Integer page;
}
