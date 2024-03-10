package com.twtw.backend.domain.plan.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDestinationResponse {

    private static final SearchDestinationResponse ON_ERROR_RESPONSE = new SearchDestinationResponse(
            new MetaDetails(true),
            List.of());

    private MetaDetails meta;
    private List<PlaceClientDetails> documents;

    public static SearchDestinationResponse onError() {
        return ON_ERROR_RESPONSE;
    }
}
