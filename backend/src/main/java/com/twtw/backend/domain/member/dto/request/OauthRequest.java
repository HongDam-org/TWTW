package com.twtw.backend.domain.member.dto.request;

import com.twtw.backend.domain.member.entity.AuthType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class OauthRequest {
    private String token;

    @Enumerated(EnumType.STRING)
    private AuthType authType;
}
