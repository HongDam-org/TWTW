package com.twtw.backend.domain.friend.controller;

import com.twtw.backend.domain.friend.dto.request.FriendRequest;
import com.twtw.backend.domain.friend.dto.request.FriendUpdateRequest;
import com.twtw.backend.domain.friend.dto.response.FriendResponse;
import com.twtw.backend.domain.friend.entity.FriendStatus;
import com.twtw.backend.domain.friend.service.FriendService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("friends")
public class FriendController {

    private final FriendService friendService;

    @GetMapping("all")
    public ResponseEntity<List<FriendResponse>> getFriends() {
        return ResponseEntity.ok(friendService.getFriends());
    }

    @GetMapping("all/cache")
    public ResponseEntity<List<FriendResponse>> getFriendsWithCache() {
        return ResponseEntity.ok(friendService.getFriendsWithCache());
    }

    @GetMapping
    public ResponseEntity<List<FriendResponse>> getFriendsByStatus(
            @RequestParam final FriendStatus friendStatus) {
        return ResponseEntity.ok(friendService.getFriendsByStatus(friendStatus));
    }

    @GetMapping("cache")
    public ResponseEntity<List<FriendResponse>> getFriendsByStatusWithCache(
            @RequestParam final FriendStatus friendStatus) {
        return ResponseEntity.ok(friendService.getFriendsByStatusWithCache(friendStatus));
    }

    @PostMapping("request")
    public ResponseEntity<Void> addRequest(@RequestBody final FriendRequest friendRequest) {
        friendService.addRequest(friendRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("status")
    public ResponseEntity<Void> updateStatus(
            @RequestBody final FriendUpdateRequest friendUpdateRequest) {
        friendService.updateStatus(friendUpdateRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("search")
    public ResponseEntity<List<FriendResponse>> getFriendByName(
            @RequestParam final String nickname) {
        return ResponseEntity.ok(friendService.getFriendByNickname(nickname));
    }

    @GetMapping("search/cache")
    public ResponseEntity<List<FriendResponse>> getFriendByNameWithCache(
            @RequestParam final String nickname) {
        return ResponseEntity.ok(friendService.getFriendByNicknameWithCache(nickname));
    }
}
