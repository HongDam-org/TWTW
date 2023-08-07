package com.twtw.backend.domain.member.service;

import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.dto.response.TokenDto;
import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.RefreshToken;
import com.twtw.backend.config.security.jwt.TokenProvider;
import com.twtw.backend.domain.member.repository.RefreshTokenRepository;
import com.twtw.backend.domain.member.client.KakaoWebClient;
import com.twtw.backend.domain.member.dto.request.OAuthRequest;
import com.twtw.backend.domain.member.entity.OAuth2Info;
import com.twtw.backend.domain.member.repository.MemberRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;
    private final KakaoWebClient kakaoWebClient;

    public AuthService(MemberRepository memberRepository,RefreshTokenRepository refreshTokenRepository,TokenProvider tokenProvider,KakaoWebClient kakaoWebClient) {
        this.memberRepository = memberRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenProvider = tokenProvider;
        this.kakaoWebClient = kakaoWebClient;
    }

    private Member toMemberEntity(MemberSaveRequest request)
    {
        Member member = new Member(request.getNickname(), request.getProfileImage(), request.getPhoneNumber(), request.getRole());
        return  member;
    }

    private OAuth2Info toOAuthInfo(String clientId, AuthType type)
    {
        OAuth2Info info = new OAuth2Info(clientId,type);

        return info;
    }

    /*
    * 1. after social SignUp
    * 2. 기본 정보 기입
    * 3. OAuth Info 저장 -> kakao , apple enum 구분
    * 4. 저장
    * 5. 토큰(jwt) 발급
    * */
    public TokenDto saveMember(MemberSaveRequest request){
        Member member = toMemberEntity(request);

        String clientId = getClientId(request.getOAuthRequest());

        member.updateOAuth(toOAuthInfo(clientId,request.getOAuthRequest().getAuthType()));
        member = memberRepository.save(member);

        UsernamePasswordAuthenticationToken credit = tokenProvider.makeCredit(member);
        TokenDto tokenDto = saveRefreshToken(credit,member.getId().toString());

        return tokenDto;
    }
    /*
    * 1.로그인(Social) 후의 토큰 발급
    * 2.JWT 토큰 발급 -> OAuth 정보 (clientId , AuthType)으로 진행
    *
    * */
    public TokenDto getTokenByOAuth(OAuthRequest request) {
        String clientId = getClientId(request);

        Optional<Member> member = memberRepository.findByOAuthIdAndAuthType(clientId,request.getAuthType());

        if(member.isPresent()) {
            Member curMember = member.get();
            UsernamePasswordAuthenticationToken credit = tokenProvider.makeCredit(curMember);
            TokenDto tokenDto = saveRefreshToken(credit,curMember.getId().toString());
            return tokenDto;
        }

        return null;
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

    private String getClientId(OAuthRequest request){
        if(request.getAuthType().equals(AuthType.KAKAO)){
            return Long.toString(kakaoWebClient.requestKakao(request.getToken()).getId());
        }
        return request.getToken();
    }

}
