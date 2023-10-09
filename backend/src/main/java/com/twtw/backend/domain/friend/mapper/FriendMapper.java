package com.twtw.backend.domain.friend.mapper;

import com.twtw.backend.domain.friend.dto.response.FriendResponse;
import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.member.entity.Member;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendMapper {

    public Friend toEntity(final Member fromMember, final Member toMember) {
        return Friend.builder().fromMember(fromMember).toMember(toMember).build();
    }

    public List<FriendResponse> toResponses(final List<Friend> friends) {
        return friends.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private FriendResponse toResponse(final Friend friend) {
        final Member fromMember = friend.getFromMember();

        return FriendResponse.builder()
                .memberId(fromMember.getId())
                .nickname(fromMember.getNickname())
                .build();
    }
}
