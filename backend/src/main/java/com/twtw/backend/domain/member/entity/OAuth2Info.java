package com.twtw.backend.domain.member.entity;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OAuth2Info {

    @Lob
    @Column(nullable = false, unique = true)
    private String clientId;

    @Enumerated(value = EnumType.STRING)
    private AuthType authType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OAuth2Info tmp = (OAuth2Info) o;

        return Objects.equals(clientId, tmp.clientId) && authType == tmp.authType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, authType);
    }
}
