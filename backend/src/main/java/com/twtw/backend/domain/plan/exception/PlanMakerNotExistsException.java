package com.twtw.backend.domain.plan.exception;

public class PlanMakerNotExistsException extends IllegalStateException {

    private static final String MESSAGE = "계획을 생성한 멤버가 존재하지 않습니다.";

    public PlanMakerNotExistsException() {
        super(MESSAGE);
    }
}
