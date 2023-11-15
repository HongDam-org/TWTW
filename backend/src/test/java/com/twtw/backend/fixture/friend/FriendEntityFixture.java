package com.twtw.backend.fixture.friend;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum FriendEntityFixture {
    FIRST_FRIEND(
            MemberEntityFixture.LOGIN_MEMBER.toEntity(),
            MemberEntityFixture.FIRST_MEMBER.toEntity()),
    SECOND_FRIEND(
            MemberEntityFixture.LOGIN_MEMBER.toEntity(),
            MemberEntityFixture.SECOND_MEMBER.toEntity()),
    THIRD_FRIEND(
            MemberEntityFixture.SECOND_MEMBER.toEntity(),
            MemberEntityFixture.FIRST_MEMBER.toEntity());

    private final Member fromMember;
    private final Member toMember;

    public Friend toEntity() {
        return new Friend(fromMember, toMember);
    }
}
