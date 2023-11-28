package com.twtw.backend.domain.path.dto.client.ped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchPedPathResponse {
    private String type;
    private List<Feature> features;
}
