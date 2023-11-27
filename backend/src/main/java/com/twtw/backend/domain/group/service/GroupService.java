package com.twtw.backend.domain.group.service;

import com.twtw.backend.domain.group.dto.request.InviteGroupRequest;
import com.twtw.backend.domain.group.dto.request.JoinGroupRequest;
import com.twtw.backend.domain.group.dto.request.MakeGroupRequest;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.ShareInfoResponse;
import com.twtw.backend.domain.group.dto.response.SimpleGroupInfoResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupInviteCode;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.group.mapper.GroupMapper;
import com.twtw.backend.domain.group.repository.GroupMemberRepository;
import com.twtw.backend.domain.group.repository.GroupRepository;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.domain.member.service.MemberService;
import com.twtw.backend.global.exception.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final AuthService authService;

    private final MemberService memberService;
    private final GroupMapper groupMapper;

    public GroupService(
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            AuthService authService,
            MemberService memberService,
            GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.authService = authService;
        this.memberService = memberService;
        this.groupMapper = groupMapper;
    }

    public GroupInfoResponse getGroupById(UUID groupId) {
        return groupMapper.toGroupInfo(
                groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new));
    }

    public Group getGroupEntity(UUID groupId) {
        return groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
    }

    public GroupMember getGroupMemberEntity(UUID groupId, UUID memberId) {
        return groupMemberRepository
                .findByGroupIdAndMemberId(groupId, memberId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public GroupInfoResponse makeGroup(MakeGroupRequest groupDto) {
        Member member = authService.getMemberByJwt();
        Group group = groupMapper.toGroupEntity(groupDto, member.getId());
        GroupMember groupMember = groupMapper.connectGroupMember(group, member);
        groupMember.changeGroupCode(GroupInviteCode.ACCEPTED);

        return groupMapper.toGroupInfo(groupRepository.save(group));
    }

    @Transactional
    public SimpleGroupInfoResponse joinGroup(JoinGroupRequest joinGroupRequest) {
        Member member = authService.getMemberByJwt();
        GroupMember groupMember =
                getGroupMemberEntity(joinGroupRequest.getGroupId(), member.getId());

        groupMember.changeGroupCode(GroupInviteCode.ACCEPTED);

        return new SimpleGroupInfoResponse(groupMember.getGroup().getId());
    }

    @Transactional
    public void changeShare(UUID id) {
        Member member = this.authService.getMemberByJwt();
        GroupInfoResponse groupInfo = getGroupById(id);

        GroupMember groupMember = getGroupMemberEntity(groupInfo.getGroupId(), member.getId());
        groupMember.changeShare();
    }

    @Transactional
    public ShareInfoResponse getShare(UUID id) {
        Member member = this.authService.getMemberByJwt();
        GroupInfoResponse groupInfo = getGroupById(id);

        GroupMember groupMember = getGroupMemberEntity(groupInfo.getGroupId(), member.getId());

        return groupMapper.toShareInfo(groupMember);
    }

    @Transactional
    public GroupInfoResponse inviteGroup(InviteGroupRequest inviteGroupRequest) {
        Group group = getGroupEntity(inviteGroupRequest.getGroupId());
        Member friend = memberService.getMemberById(inviteGroupRequest.getFriendMemberId());
        GroupMember connection = groupMapper.connectGroupMember(group, friend);

        return groupMapper.toGroupInfo(group);
    }

    public GroupInfoResponse getGroupInfoResponse(Group group) {
        return groupMapper.toGroupInfo(group);
    }
}
