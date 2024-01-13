package com.twtw.backend.domain.group.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GroupInviteCode {
    REQUESTED("요청"),
    ACCEPTED("승인"),
    DENIED("거절"),
    BLOCKED("차단"),
    EXPIRED("만료");

    private final String toKorean;
}
