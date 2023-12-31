package com.twtw.backend.domain.place.dto.response;

import com.twtw.backend.domain.plan.dto.client.PlaceClientDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class PlaceResponse {
    private List<PlaceClientDetails> results;
    private Boolean isLast;
}
