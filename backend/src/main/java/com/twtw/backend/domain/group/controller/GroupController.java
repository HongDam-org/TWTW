package com.twtw.backend.domain.group.controller;

import com.twtw.backend.domain.group.dto.request.JoinGroupRequest;
import com.twtw.backend.domain.group.dto.request.MakeGroupRequest;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.dto.response.ShareInfoResponse;
import com.twtw.backend.domain.group.service.GroupService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupInfoResponse> getGroupById(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping()
    public ResponseEntity<GroupInfoResponse> makeGroup(@RequestBody MakeGroupRequest makeGroupRequest) {
        return ResponseEntity.ok(groupService.makeGroup(makeGroupRequest));
    }

    @PostMapping("/join")
    public ResponseEntity<GroupInfoResponse> joinGroup(@RequestBody JoinGroupRequest joinGroupRequest) {
        return ResponseEntity.ok(groupService.joinGroup(joinGroupRequest));
    }

    // 그룹에 초대 하는 API (백엔드 단에서 친구 검사는 하지 말자)
    // FRIEND의 MEMBER UUID -> 잘못된 경우

    @PutMapping("/share/{id}")
    public ResponseEntity<Void> changeShare(@PathVariable UUID id) {
        groupService.changeShare(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/share/{id}")
    public ResponseEntity<ShareInfoResponse> getshare(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.getShare(id));
    }
}
