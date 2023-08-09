package com.twtw.backend.domain.member.service;

import com.twtw.backend.domain.member.repository.MemberRepository;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
}
