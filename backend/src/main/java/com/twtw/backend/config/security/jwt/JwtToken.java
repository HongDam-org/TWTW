package com.twtw.backend.config.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtToken {
    private String accessToken;
    private String refreshToken;

    @Builder
    public JwtToken(String accessToken,String refreshToken)
    {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
