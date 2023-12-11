package com.twtw.backend.domain.group.exception;

public class IllegalGroupMemberException extends IllegalArgumentException {

    private static final String MESSAGE = "그룹에 포함되지 않은 멤버입니다.";

    public IllegalGroupMemberException() {
        super(MESSAGE);
    }
}
