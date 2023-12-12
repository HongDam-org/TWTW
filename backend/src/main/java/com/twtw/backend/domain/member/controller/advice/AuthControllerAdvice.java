package com.twtw.backend.domain.member.controller.advice;

import com.twtw.backend.domain.member.exception.NicknameExistsException;
import com.twtw.backend.domain.member.exception.RefreshTokenInfoMismatchException;
import com.twtw.backend.domain.member.exception.RefreshTokenValidationException;
import com.twtw.backend.global.advice.ErrorResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(RefreshTokenInfoMismatchException.class)
    public ResponseEntity<ErrorResponse> refreshTokenInfoMismatch(
            final RefreshTokenInfoMismatchException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(RefreshTokenValidationException.class)
    public ResponseEntity<ErrorResponse> refreshTokenValidation(
            final RefreshTokenValidationException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(NicknameExistsException.class)
    public ResponseEntity<ErrorResponse> nicknameExists(final NicknameExistsException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
}
