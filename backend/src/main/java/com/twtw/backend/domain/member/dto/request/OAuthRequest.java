package com.twtw.backend.domain.member.dto.request;

import com.twtw.backend.domain.member.entity.AuthType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OAuthRequest {
    private String token;

    @Enumerated(EnumType.STRING)
    private AuthType authType;
}
