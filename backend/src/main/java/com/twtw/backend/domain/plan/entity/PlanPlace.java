package com.twtw.backend.domain.plan.entity;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;
import jakarta.persistence.Embeddable;

import lombok.*;

@Getter
@Embeddable
@AllArgsConstructor
@EqualsAndHashCode(of = {"x", "y"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanPlace {
    private String placeName;
    private String distance;
    private String placeUrl;
    private String categoryName;
    private String addressName;
    private String roadAddressName;
    private CategoryGroupCode categoryGroupCode;
    private String x;
    private String y;
}
