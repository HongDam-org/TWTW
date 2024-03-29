package com.twtw.backend.domain.plan.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm",
            timezone = "Asia/Seoul")
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
