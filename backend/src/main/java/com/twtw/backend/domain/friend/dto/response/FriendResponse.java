package com.twtw.backend.domain.friend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class FriendResponse {
    private UUID memberId;
    private String nickname;
    private String profileImage;
}
