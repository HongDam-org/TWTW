package com.twtw.backend.domain.plan.exception;

public class InvalidPlanMemberException extends IllegalArgumentException {

    private static final String MESSAGE = "계획에 추가되지 않은 멤버입니다.";

    public InvalidPlanMemberException() {
        super(MESSAGE);
    }
}
