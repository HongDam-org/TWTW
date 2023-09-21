package com.twtw.backend.domain.path.dto.client.ped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Geometry {
    private String type;
    private Double[][] coordinates;
}
