package com.twtw.backend.domain.member.controller.advice;

import com.twtw.backend.domain.member.exception.RefreshTokenInfoMismatchException;
import com.twtw.backend.domain.member.exception.RefreshTokenValidationException;
import com.twtw.backend.global.advice.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(RefreshTokenInfoMismatchException.class)
    public ResponseEntity<ErrorResponse> refreshTokenInfoMismatch(
            final RefreshTokenInfoMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(RefreshTokenValidationException.class)
    public ResponseEntity<ErrorResponse> refreshTokenValidation(
            final RefreshTokenValidationException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }
}
