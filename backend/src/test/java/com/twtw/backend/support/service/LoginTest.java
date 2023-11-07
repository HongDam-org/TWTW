package com.twtw.backend.support.service;

import static org.mockito.Mockito.when;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.support.database.DatabaseTest;
import com.twtw.backend.support.exclude.ExcludeTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@DatabaseTest
public abstract class LoginTest extends ExcludeTest {

    @MockBean protected AuthService authService;

    @Autowired protected MemberRepository memberRepository;
    protected Member loginUser;

    @BeforeEach
    public void setup() {
        final Member member = MemberEntityFixture.LOGIN_MEMBER.toEntity();
        loginUser = memberRepository.save(member);
        when(authService.getMemberByJwt()).thenReturn(loginUser);
    }
}
