package com.twtw.backend.domain.group.repository;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.fixture.group.GroupEntityFixture;
import com.twtw.backend.fixture.group.GroupMemberEntityFixture;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.fixture.place.PlaceEntityFixture;
import com.twtw.backend.support.repository.RepositoryTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GroupRepository의")
class GroupRepositoryTest extends RepositoryTest {

    @Autowired private GroupRepository groupRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private GroupMemberRepository groupMemberRepository;
    @Test
    @DisplayName("Group이 정상적으로 저장되는가")
    void makeGroup() {
        // given
        Member member1 = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());
        Member member2 = memberRepository.save(MemberEntityFixture.SECOND_MEMBER.toEntity());

        Group group = GroupEntityFixture.BTS_GROUP.toEntity();

        GroupMember groupMember1 = new GroupMember(group,member1);
        GroupMember groupMember2 = new GroupMember(group,member2);

        Place place = PlaceEntityFixture.FIRST_PLACE.toEntity();

        Plan plan = new Plan(member1,place,group);
        plan.addMember(member2);

        // when
        final Group saveGroup = groupRepository.save(group);

        // then
        assertThat(saveGroup.getGroupMembers().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("GroupMember가 조회되는가")
    void getGroupById(){
        // given
        Member member1 = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());
        Member member2 = memberRepository.save(MemberEntityFixture.SECOND_MEMBER.toEntity());

        Group group = GroupEntityFixture.BTS_GROUP.toEntity();

        GroupMember groupMember1 = new GroupMember(group,member1);
        GroupMember groupMember2 = new GroupMember(group,member2);

        final Group saveGroup = groupRepository.save(group);
        // when
        GroupMember result = groupMemberRepository.findByGroupIdAndMemberId(saveGroup.getId()
        ,member1.getId()).orElseThrow();

        // then
        assertThat(result.getGroup().getId()).isEqualTo(saveGroup.getId());
    }

    @Test
    @DisplayName("Group에서 Plan 정보가 추출되는가")
    void getPlan(){
        // given
        Member member1 = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());
        Member member2 = memberRepository.save(MemberEntityFixture.SECOND_MEMBER.toEntity());

        Group group = GroupEntityFixture.BTS_GROUP.toEntity();

        GroupMember groupMember1 = new GroupMember(group,member1);
        GroupMember groupMember2 = new GroupMember(group,member2);

        Place place = PlaceEntityFixture.FIRST_PLACE.toEntity();

        Plan plan = new Plan(member1,place,group);
        plan.addMember(member2);

        // when
        final Group saveGroup = groupRepository.save(group);

        // then
        assertThat(group.getGroupPlans().size()).isEqualTo(1);
    }
}
