package com.twtw.backend.domain.friend.service;

import com.twtw.backend.domain.friend.dto.request.FriendRequest;
import com.twtw.backend.domain.friend.dto.request.FriendUpdateRequest;
import com.twtw.backend.domain.friend.dto.response.FriendResponse;
import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.friend.repository.FriendRepository;
import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;
import com.twtw.backend.support.service.LoginTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FriendService의")
class FriendServiceTest extends LoginTest {

    @Autowired private FriendService friendService;
    @Autowired private FriendRepository friendRepository;

    @Test
    @DisplayName("요청 추가가 수행되는가")
    void addRequest() {
        // given
        final UUID id =
                memberRepository
                        .save(new Member("1", "12", new OAuth2Info("123", AuthType.APPLE), "deviceToken"))
                        .getId();

        // when
        friendService.addRequest(new FriendRequest(id));

        // then
        final List<Friend> result =
                friendRepository.findByMemberAndFriendStatus(loginUser, FriendStatus.REQUESTED);
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("상태 업데이트가 수행되는가")
    void updateStatus() {
        // given
        final Member toMember =
                memberRepository.save(new Member("1", "12", new OAuth2Info("123", AuthType.APPLE), "deviceToken"));
        final Friend friend = friendRepository.save(new Friend(loginUser, toMember));

        // when
        final FriendStatus status = FriendStatus.ACCEPTED;
        friendService.updateStatus(new FriendUpdateRequest(toMember.getId(), status));

        // then
        final Friend result = friendRepository.findById(friend.getId()).orElseThrow();
        assertThat(result.getFriendStatus()).isEqualTo(status);
    }

    @Test
    @DisplayName("친구 목록 조회가 수행되는가")
    void getFriends() {
        // given
        final Member toMember =
                memberRepository.save(new Member("1", "12", new OAuth2Info("123", AuthType.APPLE), "deviceToken"));
        friendRepository.save(new Friend(loginUser, toMember));
        friendService.updateStatus(
                new FriendUpdateRequest(toMember.getId(), FriendStatus.ACCEPTED));

        // when
        final List<FriendResponse> result = friendService.getFriends();

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("상태를 통한 친구 조회가 수행되는가")
    void getFriendsByStatus() {
        // given
        final Member toMember =
                memberRepository.save(new Member("1", "12", new OAuth2Info("123", AuthType.APPLE), "deviceToken"));
        friendRepository.save(new Friend(loginUser, toMember));

        // when
        final List<FriendResponse> result =
                friendService.getFriendsByStatus(FriendStatus.REQUESTED);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("닉네임을 통한 친구 조회가 수행되는가")
    void getFriendByNickname() {
        // given
        final String nickname = "1";
        final Member toMember =
                memberRepository.save(
                        new Member(nickname, "12", new OAuth2Info("123", AuthType.APPLE), "deviceToken"));
        final Friend expected = friendRepository.save(new Friend(loginUser, toMember));
        expected.updateStatus(FriendStatus.ACCEPTED);

        // when
        final List<FriendResponse> result = friendService.getFriendByNickname(nickname);

        // then
        assertThat(result).hasSize(1);
    }
}
