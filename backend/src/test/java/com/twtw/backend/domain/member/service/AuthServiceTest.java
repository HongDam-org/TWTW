package com.twtw.backend.domain.member.service;

import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.dto.request.OAuthRequest;
import com.twtw.backend.domain.member.dto.response.AfterLoginResponse;
import com.twtw.backend.domain.member.entity.AuthStatus;
import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.member.repository.RefreshTokenRepository;
import com.twtw.backend.support.database.DatabaseTest;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DatabaseTest
@DisplayName("AuthService의")
public class AuthServiceTest {

    @Autowired private AuthService authService;

    @Autowired private MemberRepository memberRepository;

    @Autowired private RefreshTokenRepository refreshTokenRepository;


    @Test
    @DisplayName("Kakao 회원 가입이 수행되는가")
    void saveMemberKakao(){
        // given
        MemberSaveRequest kakaoRequest = new MemberSaveRequest(
                "JinJooWon_Kakao",
                "TEST_PROFILE_IMAGE",
                new OAuthRequest(
                       "TEST_KAKAO_TOKEN",
                       AuthType.KAKAO
                )
        );
        // when
        AfterLoginResponse response = authService.saveMember(kakaoRequest);
        // then
        assertThat(response.getStatus().equals(AuthStatus.SIGNIN));
    }

    @Test
    @DisplayName("Apple 회원 가입이 수행되는가")
    void saveMemberApple(){
        //given
        MemberSaveRequest appleRequest = new MemberSaveRequest(
                "JinJooWon_Apple",
                "TEST_PROFILE_IMAGE",
                new OAuthRequest(
                        "TEST_APPLE_TOKEN",
                        AuthType.APPLE
                )
        );
        // when
        AfterLoginResponse response = authService.saveMember(appleRequest);
        // then
        assertThat(response.getStatus().equals(AuthStatus.SIGNIN));
    }


}
