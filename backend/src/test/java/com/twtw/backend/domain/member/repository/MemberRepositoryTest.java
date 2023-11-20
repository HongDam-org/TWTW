package com.twtw.backend.domain.member.repository;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.support.repository.RepositoryTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MemberRepository의")
public class MemberRepositoryTest extends RepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    @DisplayName("PK를 통한 저장/조회가 성공하는가?")
    void saveAndFindId(){
        // given
        final Member member = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        // when
        final UUID expected = member.getId();
        final Member result = memberRepository.findById(expected).orElseThrow();

        // then
        assertThat(result.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("soft delete가 수행되는가?")
    void softDelete(){
        // given
        final Member member = MemberEntityFixture.FIRST_MEMBER.toEntity();
        final UUID memberId = memberRepository.save(member).getId();

        // when
        memberRepository.deleteById(memberId);
        em.flush();
        em.clear();

        // then
        assertThat(memberRepository.findById(memberId)).isEmpty();
    }

}
