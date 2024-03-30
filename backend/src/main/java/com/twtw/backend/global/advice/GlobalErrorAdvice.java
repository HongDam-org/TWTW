package com.twtw.backend.global.advice;

import com.twtw.backend.global.exception.AuthorityException;
import com.twtw.backend.global.exception.EntityNotFoundException;
import com.twtw.backend.global.exception.WebClientResponseException;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorAdvice {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> webClientResponse(final WebClientResponseException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(final EntityNotFoundException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(AuthorityException.class)
    public ResponseEntity<ErrorResponse> authority(final AuthorityException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<ErrorResponse> interrupted(final InterruptedException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(CallNotPermittedException.class)
    public ResponseEntity<ErrorResponse> callNotPermitted(final CallNotPermittedException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> accessDenied(final AccessDeniedException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authentication(final AuthenticationException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }
}
