package com.twtw.backend.support.stub;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.friend.repository.FriendQueryRepository;
import com.twtw.backend.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class StubFriendQueryRepository implements FriendQueryRepository {

    private final Map<UUID, Friend> map;

    @Override
    public Optional<Friend> findByTwoMemberId(final UUID loginMemberId, final UUID memberId) {
        return map.values().stream()
                .filter(
                        friend ->
                                (friend.getToMember().getId().equals(loginMemberId)
                                                && friend.getFromMember().getId().equals(memberId))
                                        || (friend.getFromMember().getId().equals(loginMemberId)
                                                && friend.getToMember().getId().equals(memberId)))
                .findFirst();
    }

    @Override
    public List<Friend> findByMember(final Member member) {
        return map.values().stream()
                .filter(
                        friend ->
                                friend.getToMember().equals(member)
                                        || friend.getFromMember().equals(member))
                .toList();
    }

    @Override
    public List<Friend> findByMemberAndFriendStatus(
            final Member member, final FriendStatus friendStatus) {
        return map.values().stream()
                .filter(
                        friend ->
                                (friend.getToMember().equals(member)
                                                || friend.getFromMember().equals(member))
                                        && friend.getFriendStatus().equals(friendStatus))
                .toList();
    }

    @Override
    public List<Friend> findByMemberAndMemberNicknameContaining(
            final UUID memberId, final String nickname) {
        return map.values().stream()
                .filter(
                        friend ->
                                friend.getFriendStatus() == FriendStatus.ACCEPTED
                                        && ((friend.getToMember().getId().equals(memberId)
                                                        && friend.getFromMember()
                                                                .getNickname()
                                                                .contains(nickname))
                                                || (friend.getFromMember().getId().equals(memberId)
                                                        && friend.getToMember()
                                                                .getNickname()
                                                                .contains(nickname))))
                .toList();
    }
}
