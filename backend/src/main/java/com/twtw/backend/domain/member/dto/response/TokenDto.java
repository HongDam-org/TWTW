package com.twtw.backend.domain.member.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String accessToken;
    private String refreshToken;
}
