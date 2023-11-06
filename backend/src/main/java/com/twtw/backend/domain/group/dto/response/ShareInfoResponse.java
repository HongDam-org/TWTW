package com.twtw.backend.domain.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class ShareInfoResponse {
    private UUID groupId;
    private UUID memberId;
    private Boolean share;
}
