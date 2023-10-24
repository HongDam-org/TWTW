package com.twtw.backend.domain.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleGroupInfoResponse {
    private UUID groupId;
}
