package com.twtw.backend.domain.place.dto.client;

import com.twtw.backend.domain.plan.dto.client.MetaDetails;
import com.twtw.backend.domain.plan.dto.client.PlaceClientDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurroundPlaceResponse {

    private static final SurroundPlaceResponse ON_ERROR_RESPONSE =
            new SurroundPlaceResponse(new MetaDetails(true), List.of());

    private MetaDetails meta;
    private List<PlaceClientDetails> documents;

    public static SurroundPlaceResponse onError() {
        return ON_ERROR_RESPONSE;
    }
}
