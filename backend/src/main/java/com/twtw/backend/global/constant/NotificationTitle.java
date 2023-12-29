package com.twtw.backend.global.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationTitle {
    FRIEND_REQUEST_TITLE("친구 요청이 도착했습니다."),
    GROUP_REQUEST_TITLE("그룹 초대가 도착했습니다."),
    PLAN_REQUEST_TITLE("계획 초대가 도착했습니다."),
    DESTINATION_CHANGE_TITLE("목적지가 변경되었습니다.");

    private final String name;
}
