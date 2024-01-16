package com.twtw.backend.domain.group.service;

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
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.support.service.LoginTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GroupService의")
class GroupServiceTest extends LoginTest {
    @Autowired private GroupService groupService;

    @Autowired private GroupRepository groupRepository;

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
        Member leader = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        Group group = new Group("BABY_MONSTER", "YG_OFFICIAL_IMAGE", leader);

        Group saveGroup = groupRepository.save(group);

        saveGroup.inviteAll(List.of(loginUser));

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
        Member leader = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        Group group = new Group("BABY_MONSTER", "YG_OFFICIAL_IMAGE", leader);

        Group saveGroup = groupRepository.save(group);

        InviteGroupRequest request =
                new InviteGroupRequest(List.of(loginUser.getId()), saveGroup.getId());
        // when
        GroupInfoResponse response = groupService.inviteGroup(request);

        // then
        assertThat(response.getGroupId()).isEqualTo(saveGroup.getId());
    }

    @Test
    @DisplayName("위치 공유 정보가 반환되는가")
    void getShare() {
        // given
        Member leader = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        Group group = new Group("BABY_MONSTER", "YG_OFFICIAL_IMAGE", leader);

        Group saveGroup = groupRepository.save(group);

        group.inviteAll(List.of(loginUser));

        // when
        ShareInfoResponse response = groupService.getShare(saveGroup.getId());

        // then
        assertThat(response.getShare()).isTrue();
    }

    @Test
    @DisplayName("위치 공유를 공개 -> 비공개 변경이 가능한가")
    void changeShare() {
        // given
        Member leader = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        Group group = new Group("BABY_MONSTER", "YG_OFFICIAL_IMAGE", leader);

        Group saveGroup = groupRepository.save(group);

        group.inviteAll(List.of(loginUser));

        // when
        groupService.unShareLocation(saveGroup.getId());
        GroupMember result =
                groupMemberRepository
                        .findByGroupIdAndMemberId(saveGroup.getId(), loginUser.getId())
                        .orElseThrow();

        // then
        assertThat(result.getShare()).isFalse();
    }

    @Test
    @DisplayName("자신이 소속된 Group정보들이 반환되는가")
    void getMyGroups() {
        // given
        Member leader = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        Group group1 = new Group("BABY_MONSTER", "YG_OFFICIAL_IMAGE", leader);
        Group group2 = new Group("BLACK_PINK", "YG_OFFICIAL_IMAGE", leader);

        GroupMember groupMember1 = new GroupMember(group1, loginUser);

        GroupMember groupMember2 = new GroupMember(group2, loginUser);

        groupMember1.acceptInvite();
        groupMember2.acceptInvite();

        Group saveGroup1 = groupRepository.save(group1);
        Group saveGroup2 = groupRepository.save(group2);

        // when
        List<GroupInfoResponse> responses = groupService.getMyGroups();

        // then
        assertThat(responses).hasSize(2);
    }

    @Test
    @DisplayName("GroupId를 통한 Group 조회가 성공적인가")
    void getGroupById() {
        // given
        Member leader = memberRepository.save(MemberEntityFixture.FIRST_MEMBER.toEntity());

        Group group1 = new Group("BABY_MONSTER", "YG_OFFICIAL_IMAGE", leader);

        group1.inviteAll(List.of(loginUser));

        Group saveGroup1 = groupRepository.save(group1);
        // when

        GroupInfoResponse response = groupService.getGroupById(saveGroup1.getId());

        // then
        assertThat(response.getGroupMembers()).hasSize(1);
    }
}
