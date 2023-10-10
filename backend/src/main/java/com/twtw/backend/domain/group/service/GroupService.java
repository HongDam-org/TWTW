package com.twtw.backend.domain.group.service;

import com.twtw.backend.domain.group.dto.request.MakeGroupDto;
import com.twtw.backend.domain.group.entity.Group;
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
    private final GroupMapper groupMapper;
    private final MemberService memberService;
    public GroupService(GroupRepository groupRepository,GroupMemberRepository groupMemberRepository,AuthService authService,GroupMapper groupMapper,MemberService memberService) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.authService = authService;
        this.groupMapper = groupMapper;
        this.memberService = memberService;
    }

    @Transactional
    public void makeGroup(MakeGroupDto groupDto){
        Member member = this.authService.getMemberByJwt();
        Group group = this.groupMapper.toGroupEntity(groupDto);

        GroupMember groupMember = this.groupMapper.connectGroupMember(group,member);

        groupMemberRepository.save(groupMember);
    }
    @Transactional
    public void joinGroup(UUID groupId){
        Member member = this.authService.getMemberByJwt();
        Group group = this.groupRepository.findById(groupId).orElseThrow(EntityNotFoundException::new);

        GroupMember groupMember = this.groupMapper.connectGroupMember(group,member);

        groupMemberRepository.save(groupMember);
    }


    public void removeGroup(UUID groupId){
        // TODO()
    }


}
