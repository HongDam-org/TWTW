package com.twtw.backend.domain.friend.dto.request;

import com.twtw.backend.domain.friend.entity.FriendStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendUpdateRequest {
    private UUID memberId;
    private FriendStatus friendStatus;
}
