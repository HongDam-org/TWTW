package com.twtw.backend.domain.member.dto.response;

import com.twtw.backend.domain.member.entity.AuthStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AfterLoginResponse {

    private static final AfterLoginResponse SIGNUP = AfterLoginResponse.builder()
            .status(AuthStatus.SIGNUP).build();
    private AuthStatus status;
    private TokenDto tokenDto;

    public static AfterLoginResponse ofSignup() {
        return SIGNUP;
    }
}
