package com.twtw.backend.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.twtw.backend.domain.member.dto.response.DuplicateNicknameResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.dto.response.SearchMemberResponse;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.support.service.LoginTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MemberService의")
class MemberServiceTest extends LoginTest {
    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("닉네임 중복 체크가 제대로 동작하는가")
    void checkNickname() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());
        // when
        DuplicateNicknameResponse response = memberService.duplicateNickname(member.getNickname());
        // then
        assertTrue(response.getIsPresent());
    }

    @Test
    @DisplayName("UUID를 통해 Member 조회가 되는가")
    void getMemberById() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        // when
        Member response = memberService.getMemberById(member.getId());

        // then
        assertThat(response.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("Member가 MemberResponse로 변환이 되는가")
    void getResponseByMember() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        // when
        MemberResponse memberResponse = memberService.getResponseByMember(member);

        // then
        assertThat(memberResponse.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("Nickname을 통한 Member 검색이 수행되는가")
    void searchMemberByNickname(){
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        // when
        final SearchMemberResponse response = memberService.getMemberByNickname(member.getNickname());

        // then
        assertThat(response.getMemberResponse().getId()).isEqualTo(member.getId());
    }
}
