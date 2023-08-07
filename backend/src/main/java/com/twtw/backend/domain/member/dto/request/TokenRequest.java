package com.twtw.backend.domain.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class TokenRequest {
    String accessToken;
    String refreshToken;
}
