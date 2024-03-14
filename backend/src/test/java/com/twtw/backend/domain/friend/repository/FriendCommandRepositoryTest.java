package com.twtw.backend.domain.friend.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.support.repository.RepositoryTest;
import com.twtw.backend.utils.QueryParseUtils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

@DisplayName("FriendCommandRepository에")
class FriendCommandRepositoryTest extends RepositoryTest {

    @Autowired private FriendCommandRepository friendCommandRepository;
    @Autowired private MemberRepository memberRepository;

    @Test
    @DisplayName("저장이 수행되는가")
    void save() {
        // given
        final Member from =
                memberRepository.save(
                        new Member("1", "123", new OAuth2Info("321", AuthType.APPLE), "test"));
        final Member to =
                memberRepository.save(
                        new Member(
                                "2", "1234", new OAuth2Info("123", AuthType.KAKAO), "deviceToken"));
        final Friend friend = new Friend(from, to);

        // when
        final UUID friendId = friendCommandRepository.save(friend).getId();

        // then
        assertThat(friendId).isNotNull();
    }

    @Test
    @DisplayName("멤버와 닉네임을 통한 검색이 수행되는가")
    void findByMemberAndMemberNickname() {
        // given
        final Member from =
                memberRepository.save(
                        new Member(
                                "144",
                                "123",
                                new OAuth2Info("321", AuthType.APPLE),
                                "deviceToken1"));
        final String toMemberNickname = "277123";
        final Member to =
                memberRepository.save(
                        new Member(
                                toMemberNickname,
                                "1234",
                                new OAuth2Info("123", AuthType.KAKAO),
                                "deviceToken2"));
        final Friend friend = new Friend(from, to);
        friendCommandRepository.save(friend);
        friend.updateStatus(FriendStatus.ACCEPTED);

        // when
        final List<Friend> result =
                friendCommandRepository.findByMemberAndMemberNickname(
                        from.getId(), QueryParseUtils.parse(toMemberNickname.substring(2, 5)));

        // then
        //        assertThat(result).isNotEmpty();
    }
}
