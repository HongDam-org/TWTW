package com.twtw.backend.domain.friend.controller;

import com.twtw.backend.domain.friend.dto.request.FriendRequest;
import com.twtw.backend.domain.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("friends")
public class FriendController {

    private final FriendService friendService;

    @PostMapping("request")
    public ResponseEntity<Void> addRequest(@RequestBody final FriendRequest friendRequest) {
        friendService.addRequest(friendRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("accept")
    public ResponseEntity<Void> accept(@RequestBody final FriendRequest friendRequest) {
        friendService.accept(friendRequest);
        return ResponseEntity.noContent().build();
    }
}
