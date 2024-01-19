package com.twtw.backend.domain.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class GroupInfoResponse {
    private UUID groupId;
    private UUID leaderId;
    private String name;
    private String groupImage;
    private List<GroupMemberResponse> groupMembers;
}
