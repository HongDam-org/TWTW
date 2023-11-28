package com.twtw.backend.domain.group.mapper;

import com.twtw.backend.domain.group.dto.request.MakeGroupRequest;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.ShareInfoResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.member.entity.Member;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {
    @Mapping(target = "baseTime", ignore = true)
    GroupMember connectGroupMember(Group group, Member member);

    @Mapping(target = "baseTime", ignore = true)
    @Mapping(target = "groupMembers", ignore = true)
    @Mapping(target = "groupPlans", ignore = true)
    @Mapping(target = "name", source = "groupDto.name")
    @Mapping(target = "groupImage", source = "groupDto.groupImage")
    @Mapping(target = "leaderId", source = "leaderId")
    Group toGroupEntity(MakeGroupRequest groupDto, UUID leaderId);

    @Mapping(target = "groupId", source = "id")
    GroupInfoResponse toGroupInfo(Group group);

    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "memberId", source = "member.id")
    ShareInfoResponse toShareInfo(GroupMember groupMember);

}
