package com.twtw.backend.domain.friend.mapper;

import com.twtw.backend.domain.friend.dto.response.FriendResponse;
import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;


import java.util.List;

@Mapper(componentModel = ComponentModel.SPRING)
public interface FriendMapper2 {
    Friend toEntity(Member fromMember,Member toMember);

    List<FriendResponse> toResponses(List<Friend> friends);

    FriendResponse toResponse(Friend friend);
}
