package com.twtw.backend.domain.member.controller;

import com.twtw.backend.domain.member.dto.response.DuplicateNicknameResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.service.MemberService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<MemberResponse>> searchMemberByNickname(
            @RequestParam(name = "nickname") String nickname) {
        return ResponseEntity.ok(memberService.getMemberByNickname(nickname));
    }

    @GetMapping("/cache")
    public ResponseEntity<List<MemberResponse>> searchMemberByNicknameWithCache(
            @RequestParam(name = "nickname") String nickname
    ){
        return ResponseEntity.ok(memberService.getMemberByNicknameWithCache(nickname));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberResponse> getMemberByJwt() {
        return ResponseEntity.ok(memberService.getMemberId());
    }

    @GetMapping("/me/cache")
    public ResponseEntity<MemberResponse> getMemberByJwtWithCache(){
        return ResponseEntity.ok(memberService.getMemberIdWithCache());
    }
}
