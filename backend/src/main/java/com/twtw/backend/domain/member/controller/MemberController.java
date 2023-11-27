package com.twtw.backend.domain.member.controller;

import com.twtw.backend.domain.member.dto.response.DuplicateNicknameResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.service.MemberService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/duplicate/{name}")
    public ResponseEntity<DuplicateNicknameResponse> duplicateNickname(@PathVariable String name) {
        return ResponseEntity.ok(memberService.duplicateNickname(name));
    }

    @GetMapping("/search/{nickname}")
    public ResponseEntity<MemberResponse> searchMemberByNickname(@PathVariable String nickname){
        return ResponseEntity.ok(memberService.getMemberByNickname(nickname));
    }
}
