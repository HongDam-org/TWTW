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

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper2 {
    GroupMember connectGroupMember(Group group, Member member);

    Group toGroupEntity(MakeGroupRequest groupDto);

    @Mapping(target = "groupId", source = "id")
    GroupInfoResponse toGroupInfo(Group group);

    @Mapping(target = "groupId", source ="group.id")
    @Mapping(target = "memberId", source ="member.id")
    ShareInfoResponse toShareInfo(GroupMember groupMember);
}
