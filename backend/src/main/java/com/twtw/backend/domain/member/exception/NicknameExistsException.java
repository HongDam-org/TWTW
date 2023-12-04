package com.twtw.backend.domain.member.exception;

public class NicknameExistsException extends IllegalArgumentException {

    private static final String MESSAGE = "존재하는 닉네임입니다.";

    public NicknameExistsException() {
        super(MESSAGE);
    }
}
