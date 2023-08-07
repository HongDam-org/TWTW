package com.twtw.backend.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private final Long accessTokenExpiresIn;
}
