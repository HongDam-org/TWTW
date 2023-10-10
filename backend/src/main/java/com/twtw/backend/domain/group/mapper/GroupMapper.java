package com.twtw.backend.domain.group.mapper;

import com.twtw.backend.domain.group.dto.request.MakeGroupDto;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.member.entity.Member;

import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
    public GroupMember connectGroupMember(Group group, Member member) {
        return new GroupMember(group, member);
    }

    public Group toGroupEntity(MakeGroupDto groupDto) {
        return new Group(groupDto.getName(), groupDto.getGroupImage());
    }
}
