package com.twtw.backend.domain.member.service;

import com.twtw.backend.config.security.jwt.TokenProvider;
import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.dto.request.OAuthRequest;
import com.twtw.backend.domain.member.dto.response.AfterLoginDto;
import com.twtw.backend.domain.member.dto.response.TokenDto;
import com.twtw.backend.domain.member.entity.AuthStatus;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.RefreshToken;
import com.twtw.backend.domain.member.exception.RefreshTokenInfoMissMatchException;
import com.twtw.backend.domain.member.exception.RefreshTokenValidationException;
import com.twtw.backend.domain.member.mapper.MemberMapper;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.member.repository.RefreshTokenRepository;
import com.twtw.backend.global.exception.EntityNotFoundException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final MemberMapper memberMapper;

    public AuthService(
            MemberRepository memberRepository,
            RefreshTokenRepository refreshTokenRepository,
            TokenProvider tokenProvider,
            MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenProvider = tokenProvider;
        this.memberMapper = memberMapper;
    }

    /*
     * 1. after social SignUp
     * 2. 기본 정보 기입
     * 3. OAuth Info 저장 -> kakao , apple enum 구분
     * 4. 저장
     * 5. 토큰(jwt) 발급
     * */

    @Transactional
    public AfterLoginDto saveMember(MemberSaveRequest request) {
        Member member = memberMapper.toMemberEntity(request);

        String clientId = request.getOauthRequest().getToken();

        member.updateOAuth(
                memberMapper.toOAuthInfo(clientId, request.getOauthRequest().getAuthType()));
        memberRepository.save(member);

        UsernamePasswordAuthenticationToken credit = tokenProvider.makeCredit(member);
        TokenDto tokenDto = saveRefreshToken(credit, member.getId().toString());

        return new AfterLoginDto(AuthStatus.SIGNIN, tokenDto);
    }

    /*
     * 1.로그인(Social) 후의 토큰 발급
     * 2.JWT 토큰 발급 -> OAuth 정보 (clientId , AuthType)으로 진행
     *
     * */
    public AfterLoginDto getTokenByOAuth(OAuthRequest request) {
        String clientId = request.getToken();

        Optional<Member> member =
                memberRepository.findByOAuthIdAndAuthType(clientId, request.getAuthType());

        if (member.isPresent()) {
            Member curMember = member.get();
            UsernamePasswordAuthenticationToken credit = tokenProvider.makeCredit(curMember);
            TokenDto tokenDto = saveRefreshToken(credit, curMember.getId().toString());
            return new AfterLoginDto(AuthStatus.SIGNIN, tokenDto);
        }

        return new AfterLoginDto(AuthStatus.SIGNUP, null);
    }

    /*
     * Token 재발급
     * 1. refreshToken validate 확인
     * 2. refreshToken DB 정보 확인
     * 3. 토큰 만들어서 반환
     * */

    public TokenDto refreshToken(String accessToken, String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new RefreshTokenValidationException();
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);

        String userName = authentication.getName();

        if (!getRefreshTokenValue(userName).equals(refreshToken)) {
            throw new RefreshTokenInfoMissMatchException();
        }

        return saveRefreshToken(authentication, userName);
    }

    public String getRefreshTokenValue(String tokenKey) {
        return refreshTokenRepository.getReferenceById(tokenKey).getTokenValue();
    }

    public TokenDto saveRefreshToken(Authentication authentication, String userName) {
        TokenDto token = tokenProvider.createToken(authentication);
        refreshTokenRepository.save(new RefreshToken(userName, token.getRefreshToken()));

        return token;
    }

    public Member getMemberByJwt() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UUID id = UUID.fromString(authentication.getName());

        Optional<Member> member = memberRepository.findById(id);

        if (member.isPresent()) {
            return member.get();
        }

        throw new EntityNotFoundException();
    }
}
