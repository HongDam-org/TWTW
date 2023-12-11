package com.twtw.backend.domain.plan.dto.request;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePlanRequest {
    private UUID planId;
    private String placeName;
    private String placeUrl;
    private CategoryGroupCode categoryGroupCode;
    private String roadAddressName;
    private Double longitude;
    private Double latitude;
}
