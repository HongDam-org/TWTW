package com.twtw.backend.domain.group.controller;

import com.twtw.backend.domain.group.dto.GroupInfoDto;
import com.twtw.backend.domain.group.service.GroupService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/group")
public class GroupController {
    private final GroupService groupService;
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    //자신이 속한 그룹 반환 API
    @GetMapping("/get/{id}")
    public void getMyGroups(@PathVariable String id){}
}
