package com.twtw.backend.domain.group.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationRequest {
    private UUID groupId;
    private Double longitude;
    private Double latitude;
}
