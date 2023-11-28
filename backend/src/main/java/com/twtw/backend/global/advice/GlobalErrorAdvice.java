package com.twtw.backend.global.advice;

import com.twtw.backend.global.exception.AuthorityException;
import com.twtw.backend.global.exception.WebClientResponseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorAdvice {

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorResponse> webClientResponse(final WebClientResponseException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }

    //    @ExceptionHandler(EntityNotFoundException.class)
    //    public ResponseEntity<ErrorResponse> entityNotFound(final EntityNotFoundException e) {
    //        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                .body(new ErrorResponse(e.getMessage()));
    //    }

    @ExceptionHandler(AuthorityException.class)
    public ResponseEntity<ErrorResponse> authority(final AuthorityException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }
}
