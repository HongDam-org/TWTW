package com.twtw.backend.domain.path.dto.client.ped;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SearchPedPathRequest {
    @NotNull
    private Double startX;
    @NotNull
    private Double startY;
    @NotNull
    private Double endX;
    @NotNull
    private Double endY;
    @NotBlank
    private String startName;
    @NotBlank
    private String endName;
}
