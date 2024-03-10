package com.twtw.backend.domain.path.dto.client.ped;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchPedPathResponse {

    private static final SearchPedPathResponse ON_ERROR_RESPONSE = new SearchPedPathResponse(
            "",
            List.of());

    private String type;
    private List<Feature> features;

    public static SearchPedPathResponse onError() {
        return ON_ERROR_RESPONSE;
    }
}
