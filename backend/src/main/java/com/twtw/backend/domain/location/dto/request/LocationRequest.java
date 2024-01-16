package com.twtw.backend.domain.location.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationRequest {
    @NotNull private UUID memberId;
    @NotBlank private String nickname;
    @NotNull private Double longitude;
    @NotNull private Double latitude;
}
