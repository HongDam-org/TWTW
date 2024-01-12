package com.twtw.backend.domain.plan.dto.request;

import com.twtw.backend.domain.place.entity.CategoryGroupCode;

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
public class UpdatePlanRequest {
    private UUID planId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime planDay;

    private String name;
    private String placeName;
    private String placeUrl;
    private CategoryGroupCode categoryGroupCode;
    private String roadAddressName;
    private Double longitude;
    private Double latitude;
    private List<UUID> memberIds;
}
