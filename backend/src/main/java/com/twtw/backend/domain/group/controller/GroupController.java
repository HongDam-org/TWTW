package com.twtw.backend.domain.group.controller;

import com.twtw.backend.domain.group.dto.request.JoinGroupDto;
import com.twtw.backend.domain.group.dto.request.MakeGroupDto;
import com.twtw.backend.domain.group.dto.response.GroupInfoDto;
import com.twtw.backend.domain.group.service.GroupService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // 자신이 속한 그룹 반환 API
    @GetMapping("/get/{id}")
    public ResponseEntity<GroupInfoDto> getGroupByGroupId(@PathVariable String id) {
        return ResponseEntity.ok(groupService.getGroupByGroupId(id));
    }

    @PostMapping("/make")
    public ResponseEntity<GroupInfoDto> makeGroup(@RequestBody MakeGroupDto makeGroupDto){
        return ResponseEntity.ok(groupService.makeGroup(makeGroupDto));
    }

    @PostMapping("/join")
    public ResponseEntity<GroupInfoDto> joinGroup(@RequestBody JoinGroupDto joinGroupDto){
        return ResponseEntity.ok(groupService.joinGroup(joinGroupDto));
    }

    @PutMapping("/share/{group}")
    public ResponseEntity<Void> changeShare(@PathVariable String group){
        groupService.changeShare(group);
        return ResponseEntity.ok().build();
    }
}
