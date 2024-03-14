package com.twtw.backend.domain.friend.repository;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.member.entity.Member;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FriendQueryRepository {
    Optional<Friend> findByTwoMemberId(final UUID loginMemberId, final UUID memberId);

    List<Friend> findByMember(final Member member);

    List<Friend> findByMemberAndFriendStatus(final Member member, final FriendStatus friendStatus);
}
