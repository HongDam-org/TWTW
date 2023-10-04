package com.twtw.backend.domain.member.dto.request;

import com.twtw.backend.domain.member.entity.AuthType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OAuthRequest {
    @NotBlank
    private String token;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthType authType;
}
