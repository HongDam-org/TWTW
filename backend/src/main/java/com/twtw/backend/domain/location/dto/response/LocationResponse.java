package com.twtw.backend.domain.location.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class LocationResponse {
    private String nickname;
    private Double longitude;
    private Double latitude;
    private LocalDateTime time;
}
