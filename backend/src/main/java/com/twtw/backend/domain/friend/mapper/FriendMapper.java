package com.twtw.backend.domain.friend.mapper;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class FriendMapper {

    public Friend toEntity(final Member fromMember, final Member toMember) {
        return Friend.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .build();
    }
}
