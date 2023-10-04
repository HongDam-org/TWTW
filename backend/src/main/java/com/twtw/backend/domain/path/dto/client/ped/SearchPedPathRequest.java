package com.twtw.backend.domain.path.dto.client.ped;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank
    private String startName;
    @NotBlank
    private String endName;
}
