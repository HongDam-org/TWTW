package com.twtw.backend.domain.group.dto.request;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteGroupRequest {
    private List<UUID> friendMemberIds;
    private UUID groupId;
}
