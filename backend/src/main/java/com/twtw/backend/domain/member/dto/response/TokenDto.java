package com.twtw.backend.domain.member.dto.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
