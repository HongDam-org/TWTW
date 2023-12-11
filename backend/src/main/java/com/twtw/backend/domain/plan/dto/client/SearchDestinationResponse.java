package com.twtw.backend.domain.plan.dto.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDestinationResponse {
    private MetaDetails meta;
    private List<PlaceClientDetails> documents;
}
