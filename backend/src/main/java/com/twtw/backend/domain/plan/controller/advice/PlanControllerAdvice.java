package com.twtw.backend.domain.plan.controller.advice;

import com.twtw.backend.domain.plan.exception.InvalidPlanMemberException;
import com.twtw.backend.domain.plan.exception.PlanMakerNotExistsException;
import com.twtw.backend.global.advice.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlanControllerAdvice {

    @ExceptionHandler(InvalidPlanMemberException.class)
    public ResponseEntity<ErrorResponse> refreshTokenInfoMismatch(
            final InvalidPlanMemberException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(PlanMakerNotExistsException.class)
    public ResponseEntity<ErrorResponse> refreshTokenInfoMismatch(
            final PlanMakerNotExistsException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
}
