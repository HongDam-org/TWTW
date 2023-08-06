package com.twtw.backend.module.member.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TokenRequest {
    String accessToken;
    String refreshToken;
}
