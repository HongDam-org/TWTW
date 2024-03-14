package com.twtw.backend.support.stub;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.repository.FriendCommandRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class StubFriendCommandRepository implements FriendCommandRepository {

    private final Map<UUID, Friend> map;

    @Override
    public List<Friend> findByMemberAndMemberNickname(final UUID memberId, final String nickname) {
        return map.values().stream()
                .filter(friend -> (friend.getToMember().getId().equals(memberId) || friend.getFromMember().getId().equals(memberId)) && friend.getToMember().getNickname().toUpperCase().contains(nickname.toUpperCase()))
                .toList();
    }

    @Override
    public Friend save(final Friend friend) {
        map.put(friend.getId(), friend);
        return friend;
    }
}
