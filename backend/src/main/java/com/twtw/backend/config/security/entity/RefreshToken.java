package com.twtw.backend.config.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    private String tokenKey;

    private String tokenValue;

    public RefreshToken(String tokenKey,String tokenValue)
    {
        this.tokenKey = tokenKey;
        this.tokenValue = tokenValue;
    }
}
