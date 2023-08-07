package com.twtw.backend.module.member.dto.request;

import com.twtw.backend.module.member.entity.AuthType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OAuthRequest {
    private String clientId;

    @Enumerated(EnumType.STRING)
    private AuthType authType;
}
