package com.twtw.backend.module.member.service;

import com.twtw.backend.config.security.jwt.TokenProvider;
import com.twtw.backend.module.member.dto.TokenDto;
import com.twtw.backend.module.member.dto.request.MemberRequest;
import com.twtw.backend.module.member.entity.Member;
import com.twtw.backend.module.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

}
