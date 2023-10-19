package com.twtw.backend.domain.plan.dto.request;

import com.twtw.backend.domain.plan.dto.client.PlaceDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavePlanRequest {
    UUID groupId;
    PlaceDetails placeDetails;
}
