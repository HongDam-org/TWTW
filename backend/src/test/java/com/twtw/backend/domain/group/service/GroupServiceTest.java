package com.twtw.backend.domain.group.service;

import com.twtw.backend.domain.group.dto.request.JoinGroupRequest;
import com.twtw.backend.domain.group.dto.request.MakeGroupRequest;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.SimpleGroupInfoResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.group.repository.GroupMemberRepository;
import com.twtw.backend.domain.group.repository.GroupRepository;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.fixture.group.GroupEntityFixture;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.support.service.LoginTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GroupService의")
public class GroupServiceTest extends LoginTest {
    @Autowired private GroupService groupService;

    @Autowired private GroupRepository groupRepository;

    @Autowired private MemberRepository memberRepository;

    @Autowired private GroupMemberRepository groupMemberRepository;

    @Autowired private EntityManager em;

    @Test
    @DisplayName("makeGroup이 성공적으로 수행되는가")
    void makeGroup(){
        // given
        MakeGroupRequest request = new MakeGroupRequest("JINJOOONEKING","JIN_GROUP_IMAGE");

        // when
        GroupInfoResponse response = groupService.makeGroup(request);

        // then
        assertThat(response.getName()).isEqualTo("JINJOOONEKING");
    }

    @Test
    @DisplayName("Group에 Join할 수 있는가")
    void joinGroup(){

    }
}
