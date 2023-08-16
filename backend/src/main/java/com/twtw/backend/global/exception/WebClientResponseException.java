package com.twtw.backend.global.exception;

public class WebClientResponseException extends IllegalArgumentException {
    private static final String MESSAGE = "Rest API 호출에서 정상 응답을 받지 못했습니다.";

    public WebClientResponseException() {
        super(MESSAGE);
    }
}
