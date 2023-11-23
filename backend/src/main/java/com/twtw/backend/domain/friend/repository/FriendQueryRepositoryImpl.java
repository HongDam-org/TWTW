package com.twtw.backend.domain.friend.repository;

import static com.twtw.backend.domain.friend.entity.QFriend.friend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FriendQueryRepositoryImpl implements FriendQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Friend> findByTwoMemberId(final UUID loginMemberId, final UUID memberId) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(friend)
                        .where(
                                (friend.toMember
                                        .id
                                        .eq(loginMemberId)
                                        .and(friend.fromMember.id.eq(memberId))
                                        .or(
                                                friend.fromMember
                                                        .id
                                                        .eq(loginMemberId)
                                                        .and(friend.toMember.id.eq(memberId)))))
                        .fetchFirst());
    }

    @Override
    public List<Friend> findByMember(final Member member) {
        return jpaQueryFactory
                .selectFrom(friend)
                .where(
                        (friend.toMember.eq(member).or(friend.fromMember.eq(member)))
                                .and(friend.friendStatus.eq(FriendStatus.ACCEPTED)))
                .fetch();
    }

    @Override
    public List<Friend> findByMemberAndFriendStatus(
            final Member member, final FriendStatus friendStatus) {
        return jpaQueryFactory
                .selectFrom(friend)
                .where(
                        (friend.toMember.eq(member).or(friend.fromMember.eq(member)))
                                .and(friend.friendStatus.eq(friendStatus)))
                .fetch();
    }

    @Override
    public List<Friend> findByMemberAndMemberNickname(final Member member, final String nickname) {
        return jpaQueryFactory
                .selectFrom(friend)
                .where(
                        friend.friendStatus
                                .eq(FriendStatus.ACCEPTED)
                                .and(
                                        friend.toMember
                                                .eq(member)
                                                .and(friend.fromMember.nickname.eq(nickname))
                                                .or(
                                                        friend.fromMember
                                                                .eq(member)
                                                                .and(
                                                                        friend.toMember.nickname.eq(
                                                                                nickname)))))
                .fetch();
    }
}
