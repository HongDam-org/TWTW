package com.twtw.backend.domain.place.dto.client;

import com.twtw.backend.domain.plan.dto.client.MetaDetails;
import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SurroundPlaceResponse {
    private MetaDetails meta;
    private List<PlaceDetails> documents;
}
