package com.twtw.backend.domain.plan.dto.request;

import com.twtw.backend.domain.plan.dto.response.PlaceDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SavePlanRequest {
    private String name;
    private UUID groupId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime planDay;

    private PlaceDetails placeDetails;

    private List<UUID> memberIds;
}
