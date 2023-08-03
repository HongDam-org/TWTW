package com.twtw.backend.module.member.service;

import com.twtw.backend.config.security.jwt.TokenProvider;
import com.twtw.backend.config.security.repository.RefreshTokenRepository;
import com.twtw.backend.module.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;


    public AuthService(MemberRepository memberRepository,RefreshTokenRepository refreshTokenRepository,TokenProvider tokenProvider) {
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenProvider = tokenProvider;
    }


}
