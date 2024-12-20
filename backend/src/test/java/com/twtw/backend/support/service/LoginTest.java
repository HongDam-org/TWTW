package com.twtw.backend.support.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.domain.notification.messagequeue.FcmProducer;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.support.fake.FakeConfig;
import com.twtw.backend.support.testcontainer.ContainerTestConfig;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@ServiceTest
@ContextConfiguration(
        initializers = {ContainerTestConfig.class},
        classes = FakeConfig.class)
public abstract class LoginTest {

    @MockBean protected AuthService authService;
    protected Member loginUser;
    @MockBean private FcmProducer fcmProducer;
    @Autowired private MemberRepository memberRepository;

    @BeforeEach
    public void setup() {
        loginUser = memberRepository.save(MemberEntityFixture.LOGIN_MEMBER.toEntity());
        when(authService.getMemberByJwt()).thenReturn(loginUser);
        when(authService.getMemberIdValue()).thenReturn(loginUser.getId().toString());
        doNothing().when(fcmProducer).sendNotification(any(), any());
    }
}
