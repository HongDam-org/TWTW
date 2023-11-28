package com.twtw.backend.domain.plan.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.fixture.plan.PlanEntityFixture;
import com.twtw.backend.support.repository.RepositoryTest;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@DisplayName("PlanRepository의")
class PlanRepositoryTest extends RepositoryTest {

    @Autowired private PlanRepository planRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private EntityManager em;

    @Test
    @DisplayName("PK를 통한 조회가 수행되는가")
    void saveAndFindById() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.LOGIN_MEMBER.toEntity());
        final Plan plan = PlanEntityFixture.FIRST_PLACE.toEntity(member);

        // when
        final UUID expected = planRepository.save(plan).getId();
        final Plan result = planRepository.findById(expected).orElseThrow();

        // then
        assertThat(result.getId()).isEqualTo(expected);
    }

    @Test
    @DisplayName("soft delete가 수행되는가")
    void softDelete() {
        // given
        final Member member = MemberEntityFixture.LOGIN_MEMBER.toEntity();
        final Place place = Place.builder().longitude(1.1).latitude(2.2).placeName("스타벅스").build();

        final UUID memberId = memberRepository.save(member).getId();
        em.persist(place);

        final Group group = new Group("그룹", "http://abcdefg", memberId);

        final UUID planId = planRepository.save(new Plan(member, place, group)).getId();

        // when
        planRepository.deleteById(planId);
        em.flush();
        em.clear();

        // then
        assertThat(planRepository.findById(planId)).isEmpty();
    }
}
