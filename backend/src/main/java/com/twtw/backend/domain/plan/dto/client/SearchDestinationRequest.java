package com.twtw.backend.domain.plan.dto.client;

import com.twtw.backend.domain.plan.entity.CategoryGroupCode;
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
    private String x;
    private String y;
    private Integer page;
    private CategoryGroupCode categoryGroupCode;
}
