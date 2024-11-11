package com.twtw.backend.support.fake;

import com.twtw.backend.domain.member.entity.RefreshToken;
import com.twtw.backend.domain.member.repository.RefreshTokenRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class FakeRefreshTokenRepository implements RefreshTokenRepository {

    private final Map<UUID, RefreshToken> map = new HashMap<>();

    @Override
    public Optional<RefreshToken> findByTokenKey(final String tokenKey) {
        return map.values().stream()
                .filter(refreshToken -> refreshToken.getTokenKey().equals(tokenKey))
                .findFirst();
    }

    @Override
    public RefreshToken save(final RefreshToken refreshToken) {
        map.put(refreshToken.getId(), refreshToken);
        return refreshToken;
    }
}
