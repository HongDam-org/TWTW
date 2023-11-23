package com.twtw.backend.domain.plan.dto.client;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDestinationRequest {
    private String query;
    private Double x;
    private Double y;
    private Integer page;
    private CategoryGroupCode categoryGroupCode;

    public SearchDestinationRequest toNoDirectionRequest() {
        return new SearchDestinationRequest(query, null, null, page, categoryGroupCode);
    }
}
