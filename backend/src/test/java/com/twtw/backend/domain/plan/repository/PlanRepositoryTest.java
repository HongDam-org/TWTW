package com.twtw.backend.domain.plan.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.twtw.backend.config.database.QuerydslConfig;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.fixture.member.MemberEntityFixture;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@DataJpaTest
@Transactional
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PlanRepositoryTest {

    @Autowired private PlanRepository planRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private EntityManager em;

    @Test
    @DisplayName("soft delete가 수행되는가")
    void softDelete() {
        // given
        final Member member = MemberEntityFixture.LOGIN_MEMBER.toEntity();
        final Place place = Place.builder().x("1.1").y("2.2").placeName("스타벅스").build();

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
