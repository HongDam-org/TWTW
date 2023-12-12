package com.twtw.backend.domain.group.controller.advice;

import com.twtw.backend.domain.group.exception.IllegalGroupMemberException;
import com.twtw.backend.global.advice.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GroupControllerAdvice {

    @ExceptionHandler(IllegalGroupMemberException.class)
    public ResponseEntity<ErrorResponse> invalidFriendMember(final IllegalGroupMemberException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(e.getMessage()));
    }
}
