package com.twtw.backend.domain.friend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.support.repository.RepositoryTest;

import jakarta.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.util.List;

@Import(FriendQueryRepositoryImpl.class)
@DisplayName("FriendQueryRepository에")
class FriendQueryRepositoryImplTest extends RepositoryTest {

    @Autowired private EntityManager em;
    @Autowired private FriendQueryRepositoryImpl friendQueryRepositoryImpl;
    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("두 멤버 pk로 조회가 수행되는가")
    void findByTwoMemberId() {
        // given
        final Member from =
                memberRepository.save(
                        new Member(
                                "1", "123", new OAuth2Info("321", AuthType.APPLE), "deviceToken1"));
        final Member to =
                memberRepository.save(
                        new Member(
                                "2",
                                "1234",
                                new OAuth2Info("123", AuthType.KAKAO),
                                "deviceToken2"));
        final Friend friend = new Friend(from, to);
        em.persist(friend);

        // when
        final Friend result =
                friendQueryRepositoryImpl.findByTwoMemberId(from.getId(), to.getId()).orElseThrow();

        // then
        assertThat(result.getId()).isEqualTo(friend.getId());
    }

    @Test
    @DisplayName("멤버를 통한 조회가 수행되는가")
    void findByMember() {
        // given
        final Member from =
                memberRepository.save(
                        new Member(
                                "1", "123", new OAuth2Info("321", AuthType.APPLE), "deviceToken1"));
        final Member to =
                memberRepository.save(
                        new Member(
                                "2",
                                "1234",
                                new OAuth2Info("123", AuthType.KAKAO),
                                "deviceToken2"));
        final Friend friend = new Friend(from, to);
        em.persist(friend);
        friend.updateStatus(FriendStatus.ACCEPTED);

        // when
        final List<Friend> result = friendQueryRepositoryImpl.findByMember(from);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("멤버와 친구 상태를 통한 조회가 수행되는가")
    void findByMemberAndFriendStatus() {
        // given
        final Member from =
                memberRepository.save(
                        new Member(
                                "14312",
                                "123",
                                new OAuth2Info("321", AuthType.APPLE),
                                "deviceToken1"));
        final Member to =
                memberRepository.save(
                        new Member(
                                "24312",
                                "1234",
                                new OAuth2Info("123", AuthType.KAKAO),
                                "deviceToken2"));
        final Friend friend = new Friend(from, to);
        em.persist(friend);

        // when
        final List<Friend> result =
                friendQueryRepositoryImpl.findByMemberAndFriendStatus(
                        from, friend.getFriendStatus());

        // then
        assertThat(result).hasSize(1);
    }
}
