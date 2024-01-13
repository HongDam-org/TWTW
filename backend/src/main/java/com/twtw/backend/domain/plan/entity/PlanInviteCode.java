package com.twtw.backend.domain.plan.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlanInviteCode {
    REQUESTED("요청"),
    ACCEPTED("승인"),
    DENIED("거절"),
    EXPIRED("만료");

    private final String toKorean;
}
