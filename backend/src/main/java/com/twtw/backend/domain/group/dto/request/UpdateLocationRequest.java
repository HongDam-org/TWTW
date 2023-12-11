package com.twtw.backend.domain.group.dto.request;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLocationRequest {
    private UUID groupId;
    private Double longitude;
    private Double latitude;
}
