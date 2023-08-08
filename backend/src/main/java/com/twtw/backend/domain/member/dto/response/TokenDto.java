package com.twtw.backend.domain.member.dto.response;

import lombok.*;

@Getter
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private final Long accessTokenExpiresIn;
}
