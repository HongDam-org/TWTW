package com.twtw.backend.domain.group.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.twtw.backend.domain.group.dto.request.InviteGroupRequest;
import com.twtw.backend.domain.group.dto.request.JoinGroupRequest;
import com.twtw.backend.domain.group.dto.request.MakeGroupRequest;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.ShareInfoResponse;
import com.twtw.backend.domain.group.dto.response.SimpleGroupInfoResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.group.repository.GroupMemberRepository;
import com.twtw.backend.domain.group.repository.GroupRepository;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.support.service.LoginTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("GroupService의")
public class GroupServiceTest extends LoginTest {
    @Autowired private GroupService groupService;

    @Autowired private AuthService authService;

    @Autowired private GroupRepository groupRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private GroupMemberRepository groupMemberRepository;

    @Test
    @DisplayName("makeGroup이 성공적으로 수행되는가")
    void makeGroup() {
        // given
        final String expected = "JINJOOONEKING";

        MakeGroupRequest request = new MakeGroupRequest(expected, "JIN_GROUP_IMAGE");

        // when
        GroupInfoResponse response = groupService.makeGroup(request);

        // then
        assertThat(response.getName()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Group에 Join할 수 있는가")
    void joinGroup() {
        // given
        Member member = memberRepository.save(authService.getMemberByJwt());

        Member leader = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        Group group = new Group("BABY_MONSTER", "YG_OFFICIAL_IMAGE", leader.getId());

        GroupMember groupMember1 = new GroupMember(group, leader);

        GroupMember groupMember2 = new GroupMember(group, member);

        Group saveGroup = groupRepository.save(group);
        // when
        JoinGroupRequest request = new JoinGroupRequest(saveGroup.getId());

        SimpleGroupInfoResponse response = groupService.joinGroup(request);

        // then
        assertThat(response.getGroupId()).isEqualTo(saveGroup.getId());
    }

    @Test
    @DisplayName("Group에 초대할 수 있는가")
    void inviteGroup() {
        // given
        Member member = memberRepository.save(authService.getMemberByJwt());

        Member leader = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        Group group = new Group("BABY_MONSTER", "YG_OFFICIAL_IMAGE", leader.getId());

        GroupMember groupMember1 = new GroupMember(group, leader);

        Group saveGroup = groupRepository.save(group);

        InviteGroupRequest request = new InviteGroupRequest(member.getId(), saveGroup.getId());
        // when
        GroupInfoResponse response = groupService.inviteGroup(request);

        // then
        assertThat(response.getGroupId()).isEqualTo(saveGroup.getId());
    }

    @Test
    @DisplayName("위치 공유 정보가 반환되는가")
    void getShare() {
        // given
        Member member = memberRepository.save(authService.getMemberByJwt());

        Member leader = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        Group group = new Group("BABY_MONSTER", "YG_OFFICIAL_IMAGE", leader.getId());

        GroupMember groupMember1 = new GroupMember(group, leader);
        GroupMember groupMember2 = new GroupMember(group, member);

        Group saveGroup = groupRepository.save(group);

        // when
        ShareInfoResponse response = groupService.getShare(saveGroup.getId());

        // then
        assertThat(response.getShare()).isTrue();
    }

    @Test
    @DisplayName("위치 공유를 공개 -> 비공개 변경이 가능한가")
    void changeShare() {
        // given
        Member member = memberRepository.save(authService.getMemberByJwt());

        Member leader = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        Group group = new Group("BABY_MONSTER", "YG_OFFICIAL_IMAGE", leader.getId());

        GroupMember groupMember1 = new GroupMember(group, leader);
        GroupMember groupMember2 = new GroupMember(group, member);

        Group saveGroup = groupRepository.save(group);

        // when
        groupService.changeShare(saveGroup.getId());
        GroupMember result =
                groupMemberRepository
                        .findByGroupIdAndMemberId(saveGroup.getId(), member.getId())
                        .orElseThrow();

        // then
        assertThat(result.getShare()).isFalse();
    }
}
