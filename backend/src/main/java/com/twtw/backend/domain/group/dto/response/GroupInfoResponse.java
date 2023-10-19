package com.twtw.backend.domain.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoResponse {
    private UUID groupId;
    private UUID leaderId;
    private String name;
    private String groupImage;
}
