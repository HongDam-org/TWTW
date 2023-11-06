package com.twtw.backend.domain.member.exception;

public class RefreshTokenInfoMismatchException extends IllegalArgumentException {

    private static final String MESSAGE = "Refresh Token 정보가 일치하지 않습니다.";

    public RefreshTokenInfoMismatchException() {
        super(MESSAGE);
    }
}
