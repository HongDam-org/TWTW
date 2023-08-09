package com.twtw.backend.config.security.jwt;
import com.twtw.backend.domain.member.dto.response.TokenDto;
import com.twtw.backend.domain.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private Key key;
    private final String secretKey;
    private static final String AUTHORITIES_KEY = "auth";
    private static final Long ACCESS_TOKEN_EXPIRE_LENGTH = 60L * 60 * 24 * 1000; // 1 Day
    private static final Long REFRESH_TOKEN_EXPIRE_LENGTH = 60L * 60 * 24 * 14 * 1000; // 14 Days

    public TokenProvider(
            @Value("${jwt.secret}") String secretKey
    ){
        this.secretKey = secretKey;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    public TokenDto createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        Long now = (new Date()).getTime();
        Date validAccessDate = new Date(now + ACCESS_TOKEN_EXPIRE_LENGTH);
        Date validRefreshDate = new Date(now + REFRESH_TOKEN_EXPIRE_LENGTH);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY,authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validAccessDate)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(validRefreshDate)
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();

        return new TokenDto(accessToken,refreshToken);
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if(claims.get(AUTHORITIES_KEY) == null)
        {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<GrantedAuthority> authorities = new ArrayList<>();
        String role = claims.get(AUTHORITIES_KEY).toString();

        if(role.equals("ROLE_ADMIN")) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        else if(role.equals("ROLE_USER")){
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new UsernamePasswordAuthenticationToken(claims.getSubject(),"",authorities);

    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch (ExpiredJwtException | UnsupportedJwtException | IllegalStateException e) {
            return false;
        }
    }

    private Claims parseClaims(String accessToken) {
        try{
           Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
           return claims;
        }catch (ExpiredJwtException e)
        {
            return e.getClaims();
        }
    }

    public UsernamePasswordAuthenticationToken makeCredit(Member member)
    {
        List<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority(member.getRole().toString()));
        UsernamePasswordAuthenticationToken credit = new UsernamePasswordAuthenticationToken(member.getId().toString(),"",role);

        return credit;
    }

}
