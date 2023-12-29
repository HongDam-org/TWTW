package com.twtw.backend.global.constant;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum NotificationBody {
    GROUP_REQUEST_BODY("그룹명: %s"),
    PLAN_REQUEST_BODY("계획명: %s"),
    FRIEND_REQUEST_BODY("친구명: %s"),
    DESTINATION_CHANGE_BODY("장소명: %s");

    private final String name;

    public String toNotificationBody(final String content) {
        return String.format(name, content);
    }
}
