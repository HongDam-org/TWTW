package com.twtw.backend.domain.notification.exception;

public class NotificationException extends IllegalArgumentException {

    private static final String MESSAGE = "잘못된 알림이 요청됐습니다.";

    public NotificationException() {
        super(MESSAGE);
    }
}
