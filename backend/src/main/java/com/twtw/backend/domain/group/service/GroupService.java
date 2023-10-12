package com.twtw.backend.domain.group.service;

import com.twtw.backend.domain.group.dto.request.MakeGroupDto;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.group.mapper.GroupMapper;
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
    private final AuthService authService;
    private final GroupMapper groupMapper;

    public GroupService(
            GroupRepository groupRepository, AuthService authService, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.authService = authService;
        this.groupMapper = groupMapper;
    }

    @Transactional
    public void makeGroup(MakeGroupDto groupDto) {
        Member member = authService.getMemberByJwt();
        Group group = groupMapper.toGroupEntity(groupDto);
        GroupMember groupMember = groupMapper.connectGroupMember(group, member);

        groupRepository.save(group);
    }

    @Transactional
    public void joinGroup(UUID groupId) {
        Member member = this.authService.getMemberByJwt();
        Group group = groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);

        groupMapper.connectGroupMember(group, member);
    }

    public void removeGroup(UUID groupId) {
        // TODO()
    }
}
