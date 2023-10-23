package com.twtw.backend.domain.group.mapper;

import com.twtw.backend.domain.group.dto.request.MakeGroupRequest;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.ShareInfoResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.member.entity.Member;

import org.springframework.stereotype.Component;

@Component
public class GroupMapper {
    public GroupMember connectGroupMember(Group group, Member member) {
        return new GroupMember(group, member);
    }

    public Group toGroupEntity(MakeGroupRequest groupDto) {
        return new Group(groupDto.getName(), groupDto.getGroupImage(), groupDto.getLeaderId());
    }

    public GroupInfoResponse toGroupInfo(Group group) {
        return new GroupInfoResponse(
                group.getId(), group.getLeaderId(), group.getName(), group.getGroupImage());
    }

    public ShareInfoResponse toShareInfo(GroupMember groupMember) {
        return new ShareInfoResponse(
                groupMember.getGroup().getId(),
                groupMember.getMember().getId(),
                groupMember.getShare());
    }
}
