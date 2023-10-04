package com.twtw.backend.domain.member.exception;

public class RefreshTokenValidationException extends IllegalArgumentException {

    private static final String MESSAGE = "Refresh Token이 유효하지 않습니다.";

    public RefreshTokenValidationException() {
        super(MESSAGE);
    }
}
