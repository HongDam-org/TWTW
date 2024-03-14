package com.twtw.backend.domain.plan.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.repository.GroupRepository;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.fixture.group.GroupEntityFixture;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.fixture.plan.PlanEntityFixture;
import com.twtw.backend.support.repository.RepositoryTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@DisplayName("PlanRepository의")
class PlanRepositoryTest extends RepositoryTest {

    @Autowired private PlanRepository planRepository;
    @Autowired private GroupRepository groupRepository;
    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("PK를 통한 조회가 수행되는가")
    void saveAndFindById() {
        // given
        final Member member = memberRepository.save(MemberEntityFixture.LOGIN_MEMBER.toEntity());
        final Plan plan =
                PlanEntityFixture.FIRST_PLACE.toEntity(
                        member,
                        GroupEntityFixture.BTS_GROUP.toEntity(member),
                        LocalDateTime.of(2023, 12, 25, 13, 30));

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
        final Place place = Place.builder().longitude(1.1).latitude(2.2).placeName("스타벅스").build();

        final Member member = memberRepository.save(MemberEntityFixture.LOGIN_MEMBER.toEntity());

        final Group group = new Group("그룹", "http://abcdefg", member);

        final UUID planId =
                planRepository
                        .save(
                                new Plan(
                                        "모임명",
                                        member,
                                        place,
                                        group,
                                        LocalDateTime.of(2023, 12, 25, 13, 30)))
                        .getId();

        // when
        planRepository.deleteById(planId);

        // then
        assertThat(planRepository.findById(planId)).isEmpty();
    }

    @Test
    @DisplayName("멤버를 통한 계획 전체 조회가 수행되는가")
    void findAllPlanByMember() {
        // given
        final Place firstPlace =
                Place.builder().longitude(1.1).latitude(2.2).placeName("스타벅스").build();
        final Place secondPlace =
                Place.builder().longitude(1.1).latitude(2.2).placeName("star").build();

        final Member member = memberRepository.save(MemberEntityFixture.LOGIN_MEMBER.toEntity());
        final Member firstMember =
                memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());
        final Member secondMember =
                memberRepository.save(MemberEntityFixture.SECOND_MEMBER.toEntity());

        final Group group = new Group("그룹", "http://abcdefg", member);

        final Plan plan =
                planRepository.save(
                        new Plan(
                                "모임명",
                                member,
                                firstPlace,
                                group,
                                LocalDateTime.of(2023, 12, 25, 13, 30)));
        planRepository.save(
                new Plan(
                        "모임명",
                        member,
                        secondPlace,
                        new Group("1", "2", member),
                        LocalDateTime.of(2023, 12, 26, 13, 30)));
        plan.addMember(firstMember);
        plan.addMember(secondMember);

        // when
        final List<Plan> result = planRepository.findAllByMember(member);

        // then
        assertThat(result).hasSize(2);
    }
}
