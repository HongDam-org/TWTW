package com.twtw.backend.domain.friend.mapper;

import com.twtw.backend.domain.friend.dto.response.FriendResponse;
import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.member.entity.Member;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import java.util.List;

@Mapper(componentModel = ComponentModel.SPRING)
public interface FriendMapper {
    Friend toEntity(Member fromMember, Member toMember);

    @IterableMapping(elementTargetType = FriendResponse.class)
    List<FriendResponse> toResponses(List<Friend> friends);

    @Mapping(target = "memberId", source = "fromMember.id")
    @Mapping(target = "nickname", source = "fromMember.nickname")
    @Mapping(target = "profileImage", source = "fromMember.profileImage")
    FriendResponse toResponse(Friend friend);
}
