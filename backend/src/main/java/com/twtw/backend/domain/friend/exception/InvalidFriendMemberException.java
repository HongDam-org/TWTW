package com.twtw.backend.domain.friend.exception;

public class InvalidFriendMemberException extends IllegalArgumentException {
    private static final String MESSAGE = "본인을 친구 추가할 수 없습니다.";

    public InvalidFriendMemberException() {
        super(MESSAGE);
    }
}
