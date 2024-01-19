package com.twtw.backend.domain.group.mapper;

import com.twtw.backend.domain.group.dto.request.MakeGroupRequest;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.GroupMemberResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.member.entity.Member;

import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {
    @Mapping(target = "name", source = "groupDto.name")
    @Mapping(target = "groupImage", source = "groupDto.groupImage")
    Group toGroupEntity(MakeGroupRequest groupDto, Member leader);

    @Mapping(target = "memberId", source = "groupMember.member.id")
    @Mapping(target = "nickname", source = "groupMember.member.nickname")
    @Mapping(target = "profileImage", source = "groupMember.member.profileImage")
    @Mapping(target = "isShare", source = "groupMember.isShare")
    GroupMemberResponse toGroupMemberResponse(GroupMember groupMember);

    @IterableMapping(elementTargetType = GroupMemberResponse.class)
    List<GroupMemberResponse> toGroupMemberResponseList(List<GroupMember> groupMemberList);

    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "groupMembers", source = "groupMembers")
    GroupInfoResponse toGroupInfo(Group group, List<GroupMemberResponse> groupMembers);

    @Named("groupMemberToGroupInfoResponse")
    @Mapping(target = "groupId", source = "groupMember.group.id")
    @Mapping(target = "leaderId", source = "groupMember.group.leaderId")
    @Mapping(target = "name", source = "groupMember.group.name")
    @Mapping(target = "groupImage", source = "groupMember.group.groupImage")
    GroupInfoResponse toGroupInfoResponse(GroupMember groupMember);

    @IterableMapping(qualifiedByName = "groupMemberToGroupInfoResponse")
    List<GroupInfoResponse> toMyGroupsInfo(List<GroupMember> groupMembers);
}
