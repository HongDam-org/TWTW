package com.twtw.backend.domain.image.exception;

public class InvalidFileTypeException extends IllegalArgumentException {

    private static final String MESSAGE = "파일 형식이 잘못되었습니다.";

    public InvalidFileTypeException() {
        super(MESSAGE);
    }
}
