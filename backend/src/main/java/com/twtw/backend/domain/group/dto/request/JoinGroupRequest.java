package com.twtw.backend.domain.group.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JoinGroupRequest {
    private UUID groupId;
}
