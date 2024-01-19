package com.twtw.backend.domain.group.controller;

import com.twtw.backend.domain.group.dto.request.*;
import com.twtw.backend.domain.group.dto.response.GroupInfoResponse;
import com.twtw.backend.domain.group.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping
    public ResponseEntity<GroupInfoResponse> makeGroup(
            @RequestBody MakeGroupRequest makeGroupRequest) {
        return ResponseEntity.ok(groupService.makeGroup(makeGroupRequest));
    }

    @PostMapping("/join")
    public ResponseEntity<Void> joinGroup(
            @RequestBody JoinGroupRequest joinGroupRequest) {
        groupService.joinGroup(joinGroupRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/invite")
    public ResponseEntity<GroupInfoResponse> inviteGroup(
            @RequestBody InviteGroupRequest inviteGroupRequest) {
        return ResponseEntity.ok(groupService.inviteGroup(inviteGroupRequest));
    }

    @DeleteMapping("/invite")
    public ResponseEntity<Void> deleteInvite(
            @RequestBody DeleteGroupInviteRequest deleteGroupInviteRequest) {
        groupService.deleteInvite(deleteGroupInviteRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/share/{id}")
    public ResponseEntity<Void> shareLocation(@PathVariable UUID id) {
        groupService.shareLocation(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/unshare/{id}")
    public ResponseEntity<Void> unShareLocation(@PathVariable UUID id) {
        groupService.unShareLocation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<GroupInfoResponse>> getMyGroups() {
        return ResponseEntity.ok(groupService.getMyGroups());
    }

    @PostMapping("location")
    public ResponseEntity<Void> updateLocation(
            @RequestBody final UpdateLocationRequest updateLocationRequest) {
        groupService.updateLocation(updateLocationRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("out")
    public ResponseEntity<Void> outGroup(@RequestBody final OutGroupRequest outGroupRequest) {
        groupService.outGroup(outGroupRequest);
        return ResponseEntity.noContent().build();
    }
}
