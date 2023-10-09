package com.twtw.backend.domain.friend.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FriendStatus {
    REQUESTED("요청"),
    ACCEPTED("승인"),
    DENIED("거절"),
    BLOCKED("차단");

    private final String toKorean;
}
