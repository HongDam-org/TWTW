package com.twtw.backend.domain.friend.service;

import com.twtw.backend.domain.friend.dto.request.FriendRequest;
import com.twtw.backend.domain.friend.dto.request.FriendUpdateRequest;
import com.twtw.backend.domain.friend.dto.response.FriendResponse;
import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.friend.mapper.FriendMapper;
import com.twtw.backend.domain.friend.repository.FriendRepository;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.service.AuthService;
import com.twtw.backend.domain.member.service.MemberService;
import com.twtw.backend.global.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final MemberService memberService;
    private final AuthService authService;

    public void addRequest(final FriendRequest friendRequest) {
        final Member loginMember = authService.getMemberByJwt();
        final Member member = memberService.getMemberById(friendRequest.getMemberId());
        friendRepository.save(friendMapper.toEntity(loginMember, member));
    }

    @Transactional
    public void updateStatus(final FriendUpdateRequest friendUpdateRequest) {
        final UUID loginMemberId = authService.getMemberByJwt().getId();
        final Friend friend = getFriendById(loginMemberId, friendUpdateRequest.getMemberId());
        friend.updateStatus(friendUpdateRequest.getFriendStatus());
    }

    private Friend getFriendById(final UUID loginMemberId, final UUID memberId) {
        return friendRepository.findByTwoMemberId(memberId, loginMemberId)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<FriendResponse> getFriends() {
        final Member loginMember = authService.getMemberByJwt();
        final List<Friend> friends = friendRepository.findByMember(loginMember);
        return friendMapper.toResponses(friends);
    }

    public List<FriendResponse> getFriendsByStatus(final FriendStatus friendStatus) {
        final Member loginMember = authService.getMemberByJwt();
        final List<Friend> friends = friendRepository.findByMemberAndFriendStatus(loginMember, friendStatus);
        return friendMapper.toResponses(friends);
    }

    public List<FriendResponse> getFriendByNickname(final String nickname) {
        final Member loginMember = authService.getMemberByJwt();
        final List<Friend> friends = friendRepository.findByMemberAndMemberNickname(loginMember, nickname);
        return friendMapper.toResponses(friends);
    }
}
