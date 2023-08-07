package com.twtw.backend.module.member.service;

import com.twtw.backend.config.security.entity.RefreshToken;
import com.twtw.backend.config.security.jwt.TokenProvider;
import com.twtw.backend.config.security.repository.RefreshTokenRepository;
import com.twtw.backend.module.member.dto.TokenDto;
import com.twtw.backend.module.member.dto.request.MemberRequest;
import com.twtw.backend.module.member.entity.AuthType;
import com.twtw.backend.module.member.entity.Member;
import com.twtw.backend.module.member.entity.OAuth2Info;
import com.twtw.backend.module.member.repository.MemberRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    private Member toEntity(MemberRequest request)
    {
        Member member = new Member(request.getUserEmail(), request.getNickname(), request.getProfileImage(), request.getPhoneNumber(), request.getRole());
        return  member;
    }

    public void saveMember(MemberRequest request){
        Member member = toEntity(request);
        member.updateOAuth(new OAuth2Info("1111", AuthType.KAKAO));
        memberRepository.save(member);
    }

    /*임시 메소드*/
    public TokenDto checkMember(UUID uuid) {
        Optional<Member> member = memberRepository.findById(uuid);

        if(!member.isPresent())
            return null;

        Member curMember = member.get();
        List<GrantedAuthority> role = new ArrayList<>();
        role.add(new SimpleGrantedAuthority(curMember.getRole().toString()));
        UsernamePasswordAuthenticationToken credit = new UsernamePasswordAuthenticationToken(uuid.toString(),"",role);

        TokenDto token = tokenProvider.createToken(credit);

        refreshTokenRepository.save(new RefreshToken(curMember.getId().toString(),token.getRefreshToken()));

        return token;
    }

    /*
    * Token 재발급
    * 1. refreshToken validate 확인
    * 2. refreshToken DB 정보 확인
    * 3. 토큰 만들어서 반환
    * */

    public TokenDto refreshToken(String accessToken, String refreshToken){
        if(!tokenProvider.validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        String userName = authentication.getName();

        if(!getRefreshTokenValue(userName).equals(refreshToken)) {
            throw new RuntimeException("Refresh Token 정보가 일치하지 않습니다.");
        }

        return saveRefreshToken(authentication,userName);
    }

    public String getRefreshTokenValue(String tokenKey) {
        return refreshTokenRepository.getReferenceById(tokenKey).getTokenValue();
    }

    public TokenDto saveRefreshToken(Authentication authentication,String userName){
        TokenDto token = tokenProvider.createToken(authentication);
        refreshTokenRepository.save(new RefreshToken(userName,token.getRefreshToken()));

        return token;
    }


}
