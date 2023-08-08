package com.twtw.backend.domain.member.dto.request;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {
    private String accessToken;
    private String refreshToken;
}
