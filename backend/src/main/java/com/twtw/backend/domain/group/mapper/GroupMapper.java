package com.twtw.backend.domain.group.mapper;

import com.twtw.backend.domain.group.dto.request.MakeGroupDto;
import com.twtw.backend.domain.group.dto.response.GroupInfoDto;
import com.twtw.backend.domain.group.dto.response.ShareInfo;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.member.entity.Member;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GroupMapper {
    public GroupMember connectGroupMember(Group group, Member member) {
        return new GroupMember(group, member);
    }

    public Group toGroupEntity(MakeGroupDto groupDto) {
        return new Group(
                groupDto.getName(),
                groupDto.getGroupImage(),
                UUID.fromString(groupDto.getLeaderId()));
    }

    public GroupInfoDto toGroupInfo(Group group) {
        return new GroupInfoDto(
                group.getId(), group.getLeaderId(), group.getName(), group.getGroupImage());
    }

    public ShareInfo toShareInfo(GroupMember groupMember) {
        return new ShareInfo(
                groupMember.getGroup().getId(),
                groupMember.getMember().getId(),
                groupMember.getShare());
    }
}
