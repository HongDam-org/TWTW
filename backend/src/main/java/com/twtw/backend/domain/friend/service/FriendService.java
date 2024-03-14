package com.twtw.backend.domain.friend.service;

import com.twtw.backend.domain.friend.dto.request.FriendRequest;
import com.twtw.backend.domain.friend.dto.request.FriendUpdateRequest;
import com.twtw.backend.domain.friend.dto.response.FriendResponse;
import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.friend.mapper.FriendMapper;
import com.twtw.backend.domain.friend.repository.FriendCommandRepository;
import com.twtw.backend.domain.friend.repository.FriendQueryRepository;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.domain.member.service.MemberService;
import com.twtw.backend.domain.notification.dto.NotificationRequest;
import com.twtw.backend.domain.notification.messagequeue.FcmProducer;
import com.twtw.backend.global.constant.NotificationBody;
import com.twtw.backend.global.constant.NotificationTitle;
import com.twtw.backend.global.exception.EntityNotFoundException;
import com.twtw.backend.utils.QueryParseUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendQueryRepository friendQueryRepository;
    private final FriendCommandRepository friendCommandRepository;
    private final FriendMapper friendMapper;
    private final MemberService memberService;
    private final AuthService authService;
    private final FcmProducer fcmProducer;

    @Transactional
    public void addRequest(final FriendRequest friendRequest) {
        final Member loginMember = authService.getMemberByJwt();
        final Member member = memberService.getMemberById(friendRequest.getMemberId());

        createFriendRequestByNicknameOrder(loginMember, member);

        sendNotification(
                member.getDeviceTokenValue(), loginMember.getNickname(), loginMember.getId());
    }

    private void createFriendRequestByNicknameOrder(final Member loginMember, final Member member) {
        if (loginMember.hasFasterNickname(member)) {
            createFriendRequest(loginMember, member);
            return;
        }
        createFriendRequest(member, loginMember);
    }

    public void createFriendRequest(final Member fromMember, final Member toMember) {
        friendCommandRepository.save(friendMapper.toEntity(fromMember, toMember));
    }

    private void sendNotification(final String deviceToken, final String nickname, final UUID id) {
        fcmProducer.sendNotification(
                new NotificationRequest(
                        deviceToken,
                        NotificationTitle.FRIEND_REQUEST_TITLE.getName(),
                        NotificationBody.FRIEND_REQUEST_BODY.toNotificationBody(nickname),
                        id.toString()));
    }

    @Transactional
    public void updateStatus(final FriendUpdateRequest friendUpdateRequest) {
        final UUID loginMemberId = authService.getMemberByJwt().getId();
        final Friend friend = getFriendById(loginMemberId, friendUpdateRequest.getMemberId());

        friend.updateStatus(friendUpdateRequest.getFriendStatus());
    }

    private Friend getFriendById(final UUID loginMemberId, final UUID memberId) {
        return friendQueryRepository
                .findByTwoMemberId(loginMemberId, memberId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public String getMemberIdValue() {
        return authService.getMemberIdValue();
    }

    @CacheEvict(
            value = "getFriendsWithCache",
            key = "'getFriendsWithCache'.concat(#root.target.getMemberIdValue())",
            cacheManager = "cacheManager")
    @Transactional(readOnly = true)
    public List<FriendResponse> getFriends() {
        return getFriendResponses();
    }

    @Cacheable(
            value = "getFriendsWithCache",
            key = "'getFriendsWithCache'.concat(#root.target.getMemberIdValue())",
            cacheManager = "cacheManager",
            unless = "#result.size() <= 0")
    @Transactional(readOnly = true)
    public List<FriendResponse> getFriendsWithCache() {
        return getFriendResponses();
    }

    private List<FriendResponse> getFriendResponses() {
        final Member loginMember = authService.getMemberByJwt();
        final List<Member> friends =
                friendQueryRepository.findByMember(loginMember).stream()
                        .map(friend -> friend.getFriendMember(loginMember))
                        .toList();

        return friendMapper.toResponses(friends);
    }

    @CacheEvict(
            value = "getFriendsByStatusWithCache",
            key =
                    "'getFriendsWithCache'.concat(#root.target.getMemberIdValue()).concat(#friendStatus.name())",
            cacheManager = "cacheManager")
    @Transactional(readOnly = true)
    public List<FriendResponse> getFriendsByStatus(final FriendStatus friendStatus) {
        return getFriendResponsesByStatus(friendStatus);
    }

    @Cacheable(
            value = "getFriendsByStatusWithCache",
            key =
                    "'getFriendsWithCache'.concat(#root.target.getMemberIdValue()).concat(#friendStatus.name())",
            cacheManager = "cacheManager",
            unless = "#result.size() <= 0")
    @Transactional(readOnly = true)
    public List<FriendResponse> getFriendsByStatusWithCache(final FriendStatus friendStatus) {
        return getFriendResponsesByStatus(friendStatus);
    }

    private List<FriendResponse> getFriendResponsesByStatus(final FriendStatus friendStatus) {
        final Member loginMember = authService.getMemberByJwt();
        final List<Member> friends =
                friendQueryRepository
                        .findByMemberAndFriendStatus(loginMember, friendStatus)
                        .stream()
                        .map(friend -> friend.getFriendMember(loginMember))
                        .toList();

        return friendMapper.toResponses(friends);
    }

    @CacheEvict(
            value = "getFriendsByNicknameWithCache",
            key = "'getFriendsWithCache'.concat(#root.target.getMemberIdValue()).concat(#nickname)",
            cacheManager = "cacheManager")
    @Transactional(readOnly = true)
    public List<FriendResponse> getFriendByNickname(final String nickname) {
        return getFriendResponsesByNickname(nickname);
    }

    @Cacheable(
            value = "getFriendsByNicknameWithCache",
            key = "'getFriendsWithCache'.concat(#root.target.getMemberIdValue()).concat(#nickname)",
            cacheManager = "cacheManager",
            unless = "#result.size() <= 0")
    @Transactional(readOnly = true)
    public List<FriendResponse> getFriendByNicknameWithCache(final String nickname) {
        return getFriendResponsesByNickname(nickname);
    }

    private List<FriendResponse> getFriendResponsesByNickname(final String nickname) {
        final Member loginMember = authService.getMemberByJwt();
        final List<Member> friends = findFriendsByNickname(loginMember, nickname);

        return friendMapper.toResponses(friends);
    }

    private List<Member> findFriendsByNickname(final Member loginMember, final String nickname) {
        if (nickname.length() < 2) {
            return friendQueryRepository
                    .findByMemberAndMemberNicknameContaining(loginMember.getId(), nickname)
                    .stream()
                    .map(friend -> friend.getFriendMember(loginMember))
                    .toList();
        }
        return friendCommandRepository
                .findByMemberAndMemberNickname(loginMember.getId(), QueryParseUtils.parse(nickname))
                .stream()
                .filter(friend -> friend.nicknameContains(nickname))
                .map(friend -> friend.getFriendMember(loginMember))
                .toList();
    }
}
