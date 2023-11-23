package com.twtw.backend.domain.friend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.support.repository.RepositoryTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@DisplayName("FriendRepository에")
class FriendRepositoryTest extends RepositoryTest {

    @Autowired private FriendRepository friendRepository;
    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("저장이 수행되는가")
    void save() {
        // given
        final Member from =
                memberRepository.save(
                        new Member("1", "123", new OAuth2Info("321", AuthType.APPLE)));
        final Member to =
                memberRepository.save(
                        new Member("2", "1234", new OAuth2Info("123", AuthType.KAKAO)));
        final Friend friend = new Friend(from, to);

        // when
        final UUID friendId = friendRepository.save(friend).getId();

        // then
        assertThat(friendId).isNotNull();
    }

    @Test
    @DisplayName("두 멤버 pk로 조회가 수행되는가")
    void findByTwoMemberId() {
        // given
        final Member from =
                memberRepository.save(
                        new Member("1", "123", new OAuth2Info("321", AuthType.APPLE)));
        final Member to =
                memberRepository.save(
                        new Member("2", "1234", new OAuth2Info("123", AuthType.KAKAO)));
        final Friend friend = new Friend(from, to);
        final Friend expected = friendRepository.save(friend);

        // when
        final Friend result =
                friendRepository.findByTwoMemberId(from.getId(), to.getId()).orElseThrow();

        // then
        assertThat(result.getId()).isEqualTo(expected.getId());
    }

    @Test
    @DisplayName("멤버를 통한 조회가 수행되는가")
    void findByMember() {
        // given
        final Member from =
                memberRepository.save(
                        new Member("1", "123", new OAuth2Info("321", AuthType.APPLE)));
        final Member to =
                memberRepository.save(
                        new Member("2", "1234", new OAuth2Info("123", AuthType.KAKAO)));
        final Friend friend = new Friend(from, to);
        final Friend expected = friendRepository.save(friend);
        expected.updateStatus(FriendStatus.ACCEPTED);

        // when
        final List<Friend> result = friendRepository.findByMember(from);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("멤버와 친구 상태를 통한 조회가 수행되는가")
    void findByMemberAndFriendStatus() {
        // given
        final Member from =
                memberRepository.save(
                        new Member("1", "123", new OAuth2Info("321", AuthType.APPLE)));
        final Member to =
                memberRepository.save(
                        new Member("2", "1234", new OAuth2Info("123", AuthType.KAKAO)));
        final Friend friend = new Friend(from, to);
        friendRepository.save(friend);

        // when
        final List<Friend> result =
                friendRepository.findByMemberAndFriendStatus(from, friend.getFriendStatus());

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("멤버와 닉네임을 통한 검색이 수행되는가")
    void findByMemberAndMemberNickname() {
        // given
        final Member from =
                memberRepository.save(
                        new Member("1", "123", new OAuth2Info("321", AuthType.APPLE)));
        final String friendNickname = "2";
        final Member to =
                memberRepository.save(
                        new Member(friendNickname, "1234", new OAuth2Info("123", AuthType.KAKAO)));
        final Friend friend = new Friend(from, to);
        final Friend expected = friendRepository.save(friend);
        expected.updateStatus(FriendStatus.ACCEPTED);

        // when
        final List<Friend> result =
                friendRepository.findByMemberAndMemberNickname(from, friendNickname);

        // then
        assertThat(result).hasSize(1);
    }
}
