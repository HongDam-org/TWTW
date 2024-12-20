package com.twtw.backend.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.dto.request.OAuthRequest;
import com.twtw.backend.domain.member.dto.response.AfterLoginResponse;
import com.twtw.backend.domain.member.entity.AuthStatus;
import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.support.fake.FakeConfig;
import com.twtw.backend.support.service.ServiceTest;
import com.twtw.backend.support.testcontainer.ContainerTestConfig;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ServiceTest
@ContextConfiguration(
        initializers = {ContainerTestConfig.class},
        classes = FakeConfig.class)
@DisplayName("AuthService의")
class AuthServiceTest {

    @Autowired private AuthService authService;
    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("Kakao 회원 가입이 수행되는가")
    void saveMemberKakao() {
        // given
        MemberSaveRequest kakaoRequest =
                new MemberSaveRequest(
                        "Kakao",
                        "TEST_PROFILE_IMAGE",
                        "deviceToken",
                        new OAuthRequest("TEST_KAKAO_TOKEN", AuthType.KAKAO));

        // when
        AfterLoginResponse response = authService.saveMember(kakaoRequest);

        // then
        assertThat(response.getStatus()).isEqualTo(AuthStatus.SIGNIN);
    }

    @Test
    @DisplayName("Apple 회원 가입이 수행되는가")
    void saveMemberApple() {
        // given
        MemberSaveRequest appleRequest =
                new MemberSaveRequest(
                        "Apple",
                        "TEST_PROFILE_IMAGE",
                        "deviceToken",
                        new OAuthRequest("TEST_APPLE_TOKEN", AuthType.APPLE));

        // when
        AfterLoginResponse response = authService.saveMember(appleRequest);

        // then
        assertThat(response.getStatus()).isEqualTo(AuthStatus.SIGNIN);
    }

    @Test
    @DisplayName("로그인 후 회원에 대해 토큰이 재발급되는가")
    void getTokenByOAuthSuccess() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        final OAuthRequest request =
                new OAuthRequest(
                        member.getOauthInfo().getClientId(), member.getOauthInfo().getAuthType());

        // when
        AfterLoginResponse response = authService.getTokenByOAuth(request);

        // then
        assertThat(response.getStatus()).isEqualTo(AuthStatus.SIGNIN);
    }
}
