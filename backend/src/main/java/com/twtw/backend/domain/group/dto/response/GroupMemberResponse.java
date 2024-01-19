package com.twtw.backend.domain.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class GroupMemberResponse {
    private UUID memberId;
    private String nickname;
    private String profileImage;
    private Boolean isShare;
}
