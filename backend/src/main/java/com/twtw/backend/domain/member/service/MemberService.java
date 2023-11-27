package com.twtw.backend.domain.member.service;

import com.twtw.backend.domain.member.dto.response.DuplicateNicknameResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.mapper.MemberMapper;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.global.exception.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public DuplicateNicknameResponse duplicateNickname(String nickName) {
        Optional<Member> member = memberRepository.findByNickname(nickName);

        if (member.isPresent()) {
            return new DuplicateNicknameResponse(true);
        }

        return new DuplicateNicknameResponse(false);
    }

    public Member getMemberById(UUID id) {
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        return member;
    }


    public MemberResponse getMemberByNickname(String nickname){
        Member member = memberRepository.findByNickname(nickname).orElseThrow(EntityNotFoundException::new);

        return memberMapper.toMemberResponse(member);
    }

    public MemberResponse getResponseByMember(Member member) {
        return memberMapper.toMemberResponse(member);
    }
}
