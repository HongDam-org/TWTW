package com.twtw.backend.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {
    @NotBlank private String accessToken;
    @NotBlank private String refreshToken;
}
