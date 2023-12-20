package com.twtw.backend.domain.plan.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.twtw.backend.domain.plan.dto.response.PlaceDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavePlanRequest {
    private UUID groupId;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm",
            timezone = "Asia/Seoul")
    private LocalDateTime planDay;

    private PlaceDetails placeDetails;
}
