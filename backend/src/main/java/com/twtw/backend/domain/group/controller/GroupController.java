package com.twtw.backend.domain.group.controller;

import com.twtw.backend.domain.group.dto.request.JoinGroupDto;
import com.twtw.backend.domain.group.dto.request.MakeGroupDto;
import com.twtw.backend.domain.group.dto.response.GroupInfoDto;
import com.twtw.backend.domain.group.dto.response.ShareInfo;
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

    // 자신이 속한 그룹 반환 API
    @GetMapping("/{id}")
    public ResponseEntity<GroupInfoDto> getGroupByGroupId(@PathVariable UUID id) {
        return ResponseEntity.ok(groupService.getGroupById(id));
    }

    @PostMapping()
    public ResponseEntity<GroupInfoDto> makeGroup(@RequestBody MakeGroupDto makeGroupDto){
        return ResponseEntity.ok(groupService.makeGroup(makeGroupDto));
    }

    @PostMapping("/join")
    public ResponseEntity<GroupInfoDto> joinGroup(@RequestBody JoinGroupDto joinGroupDto){
        return ResponseEntity.ok(groupService.joinGroup(joinGroupDto));
    }

    @PutMapping("/share/{id}")
    public ResponseEntity<Void> changeShare(@PathVariable UUID id){
        groupService.changeShare(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/share/{id}")
    public ResponseEntity<ShareInfo> getshare(@PathVariable UUID id){
        return ResponseEntity.ok(groupService.getShare(id));
    }
}
