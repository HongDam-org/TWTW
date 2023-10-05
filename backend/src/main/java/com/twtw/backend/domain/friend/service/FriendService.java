package com.twtw.backend.domain.friend.service;

import com.twtw.backend.domain.friend.dto.request.FriendRequest;
import com.twtw.backend.domain.friend.mapper.FriendMapper;
import com.twtw.backend.domain.friend.repository.FriendRepository;
import com.twtw.backend.domain.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final AuthService authService;

    public void addRequest(final FriendRequest friendRequest) {
        final UUID toMemberId = friendRequest.getMemberId();
    }

    public void accept(final FriendRequest friendRequest) {
        final UUID fromMemberId = friendRequest.getMemberId();
    }
}
