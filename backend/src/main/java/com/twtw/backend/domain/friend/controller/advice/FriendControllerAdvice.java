package com.twtw.backend.domain.friend.controller.advice;

import com.twtw.backend.domain.friend.exception.InvalidFriendMemberException;
import com.twtw.backend.global.advice.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FriendControllerAdvice {

    @ExceptionHandler(InvalidFriendMemberException.class)
    public ResponseEntity<ErrorResponse> invalidFriendMember(final InvalidFriendMemberException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(e.getMessage()));
    }
}
