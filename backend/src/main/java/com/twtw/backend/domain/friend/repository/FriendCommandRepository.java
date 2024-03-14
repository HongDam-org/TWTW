package com.twtw.backend.domain.friend.repository;

import com.twtw.backend.domain.friend.entity.Friend;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FriendCommandRepository {
    Friend save(final Friend friend);
    List<Friend> findByMemberAndMemberNickname(final UUID memberId, final String nickname);
}
