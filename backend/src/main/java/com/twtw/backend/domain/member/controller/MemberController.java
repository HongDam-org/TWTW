package com.twtw.backend.domain.member.controller;

import com.twtw.backend.domain.member.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;
    public MemberController(MemberService memberService)
    {
        this.memberService = memberService;
    }


}
