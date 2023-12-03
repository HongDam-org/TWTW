package com.twtw.backend.domain.group.mapper;

import com.twtw.backend.domain.group.dto.request.MakeGroupRequest;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.ShareInfoResponse;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.member.entity.Member;
import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {
    @Mapping(target = "name", source = "groupDto.name")
    @Mapping(target = "groupImage", source = "groupDto.groupImage")
    Group toGroupEntity(MakeGroupRequest groupDto, Member leader);

    @Mapping(target = "groupId", source = "id")
    GroupInfoResponse toGroupInfo(Group group);

    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "memberId", source = "member.id")
    ShareInfoResponse toShareInfo(GroupMember groupMember);

    @Named("groupMemberToGroupInfoResponse")
    @Mapping(target = "groupId", source = "groupMember.group.id")
    @Mapping(target = "leaderId", source = "groupMember.group.leaderId")
    @Mapping(target = "name", source = "groupMember.group.name")
    @Mapping(target = "groupImage", source = "groupMember.group.groupImage")
    GroupInfoResponse toGroupInfoResponse(GroupMember groupMember);

    @IterableMapping(qualifiedByName = "groupMemberToGroupInfoResponse")
    List<GroupInfoResponse> toMyGroupsInfo(List<GroupMember> groupMembers);
}
