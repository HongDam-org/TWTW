package com.twtw.backend.domain.group.service;

import com.twtw.backend.domain.group.dto.request.JoinGroupDto;
import com.twtw.backend.domain.group.dto.request.MakeGroupDto;
import com.twtw.backend.domain.group.dto.response.GroupInfoDto;
import com.twtw.backend.domain.group.dto.response.ShareInfo;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.group.mapper.GroupMapper;
import com.twtw.backend.domain.group.repository.GroupMemberRepository;
import com.twtw.backend.domain.group.repository.GroupRepository;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.global.exception.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final AuthService authService;
    private final GroupMapper groupMapper;

    public GroupService(
            GroupRepository groupRepository,
            GroupMemberRepository groupMemberRepository,
            AuthService authService,
            GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.authService = authService;
        this.groupMapper = groupMapper;
    }

    public GroupInfoDto getGroupById(UUID groupId) {
        return groupMapper.toGroupInfo(
                groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new));
    }

    public Group getGroupEntity(UUID groupId){
        return  groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public GroupInfoDto makeGroup(MakeGroupDto groupDto) {
        Member member = authService.getMemberByJwt();
        Group group = groupMapper.toGroupEntity(groupDto);
        GroupMember groupMember = groupMapper.connectGroupMember(group, member);

        return groupMapper.toGroupInfo(groupRepository.save(group));
    }

    @Transactional
    public GroupInfoDto joinGroup(JoinGroupDto joinGroupDto) {
        Member member = this.authService.getMemberByJwt();
        Group group =
                groupRepository
                        .findById(UUID.fromString(joinGroupDto.getGroupId()))
                        .orElseThrow(EntityNotFoundException::new);

        groupMapper.connectGroupMember(group, member);

        return groupMapper.toGroupInfo(group);
    }

    @Transactional
    public void changeShare(UUID id) {
        Member member = this.authService.getMemberByJwt();
        GroupInfoDto groupInfo = getGroupById(id);

        GroupMember groupMember =
                groupMemberRepository
                        .findByGroupIdAndMemberId(groupInfo.getGroupId(), member.getId())
                        .orElseThrow(EntityNotFoundException::new);
        groupMember.changeShare();
    }

    @Transactional
    public ShareInfo getShare(UUID id) {
        Member member = this.authService.getMemberByJwt();
        GroupInfoDto groupInfo = getGroupById(id);

        GroupMember groupMember =
                groupMemberRepository
                        .findByGroupIdAndMemberId(groupInfo.getGroupId(), member.getId())
                        .orElseThrow(EntityNotFoundException::new);

        return groupMapper.toShareInfo(groupMember);
    }
}
