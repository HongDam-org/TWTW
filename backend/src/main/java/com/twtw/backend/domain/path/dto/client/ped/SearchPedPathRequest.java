package com.twtw.backend.domain.path.dto.client.ped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchPedPathRequest {
    private String startX;
    private String startY;
    private String endX;
    private String endY;
    private String startName;
    private String endName;
}
