package com.twtw.backend.config.security.jwt;

import com.twtw.backend.domain.member.dto.response.TokenDto;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.Role;
import com.twtw.backend.global.exception.AuthorityException;

import io.jsonwebtoken.*;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final Key key;
    private static final String AUTHORITIES_KEY = "auth";
    private static final Long ACCESS_TOKEN_EXPIRE_LENGTH = 60L * 60 * 24 * 1000; // 1 Day
    private static final Long REFRESH_TOKEN_EXPIRE_LENGTH = 60L * 60 * 24 * 14 * 1000; // 14 Days

    public TokenDto createToken(Authentication authentication) {
        String authorities =
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","));

        Long now = (new Date()).getTime();
        Date validAccessDate = new Date(now + ACCESS_TOKEN_EXPIRE_LENGTH);
        Date validRefreshDate = new Date(now + REFRESH_TOKEN_EXPIRE_LENGTH);

        String accessToken =
                Jwts.builder()
                        .setSubject(authentication.getName())
                        .claim(AUTHORITIES_KEY, authorities)
                        .signWith(key, SignatureAlgorithm.HS512)
                        .setExpiration(validAccessDate)
                        .compact();

        String refreshToken =
                Jwts.builder()
                        .setExpiration(validRefreshDate)
                        .signWith(key, SignatureAlgorithm.HS512)
                        .compact();

        return new TokenDto(accessToken, refreshToken);
    }

    public Optional<Authentication> getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        return Optional.ofNullable(claims.get(AUTHORITIES_KEY))
                .map(
                        auth -> {
                            Collection<GrantedAuthority> authorities = new ArrayList<>();
                            String role = claims.get(AUTHORITIES_KEY).toString();

                            addRole(role, authorities);

                            return new UsernamePasswordAuthenticationToken(
                                    claims.getSubject(), "", authorities);
                        });
    }

    private void addRole(final String role, final Collection<GrantedAuthority> authorities) {
        if (role.equals(Role.ROLE_ADMIN.name())) {
            authorities.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.name()));
            return;
        }
        if (role.equals(Role.ROLE_USER.name())) {
            authorities.add(new SimpleGrantedAuthority(Role.ROLE_USER.name()));
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException
                | UnsupportedJwtException
                | IllegalStateException
                | MalformedJwtException e) {
            return false;
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new AuthorityException();
        }
    }

    public UsernamePasswordAuthenticationToken makeCredit(Member member) {
        List<GrantedAuthority> role =
                List.of(new SimpleGrantedAuthority(member.getRole().toString()));

        return new UsernamePasswordAuthenticationToken(member.getId().toString(), "", role);
    }
}
