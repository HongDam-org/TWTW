package com.twtw.backend.domain.member.dto.response;

import com.twtw.backend.domain.member.entity.AuthStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AfterLoginResponse {
    private AuthStatus status;
    private TokenDto tokenDto;
}
