package com.twtw.backend.domain.path.dto.client.ped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SearchPedPathRequest {
    private Double startX;
    private Double startY;
    private Double endX;
    private Double endY;
    private String startName;
    private String endName;
}
