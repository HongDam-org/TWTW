package com.twtw.backend.domain.group.service;

import com.twtw.backend.domain.group.repository.GroupMemberRepository;
import com.twtw.backend.domain.group.repository.GroupRepository;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;

    public GroupService(GroupRepository groupRepository,GroupMemberRepository groupMemberRepository){
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
    }
}
