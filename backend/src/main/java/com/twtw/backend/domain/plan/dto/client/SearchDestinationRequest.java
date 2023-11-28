package com.twtw.backend.domain.plan.dto.client;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchDestinationRequest {
    private String query;
    private Double longitude;
    private Double latitude;
    private Integer page;
    private CategoryGroupCode categoryGroupCode;

    public SearchDestinationRequest toNoDirectionRequest() {
        return new SearchDestinationRequest(query, null, null, page, categoryGroupCode);
    }
}
