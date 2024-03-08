package com.twtw.backend.support.service;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.domain.notification.messagequeue.FcmProducer;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.support.database.DatabaseTest;
import com.twtw.backend.support.database.ResetDatabase;
import com.twtw.backend.support.testcontainer.ContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@DatabaseTest
@Import(ResetDatabase.class)
@ContextConfiguration(initializers = ContainerTest.class)
public abstract class LoginTest {

    @MockBean protected AuthService authService;
    @MockBean protected FcmProducer fcmProducer;

    @Autowired protected MemberRepository memberRepository;
    @Autowired private ResetDatabase resetDatabase;
    protected Member loginUser;

    @BeforeEach
    public void setup() {
        resetDatabase.reset();
        final Member member = MemberEntityFixture.LOGIN_MEMBER.toEntity();
        loginUser = memberRepository.save(member);
        when(authService.getMemberByJwt()).thenReturn(loginUser);
    }

    @BeforeEach
    void setUp() {
        doNothing().when(fcmProducer).sendNotification(any());
    }
}
