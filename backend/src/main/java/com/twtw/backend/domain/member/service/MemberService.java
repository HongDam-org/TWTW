package com.twtw.backend.domain.member.service;

import com.twtw.backend.domain.member.dto.response.DuplicateNicknameDto;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.repository.MemberRepository;

import com.twtw.backend.global.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public DuplicateNicknameDto duplicateNickname(String nickName){
        Optional<Member> member = memberRepository.findByNickname(nickName);

        if(member.isPresent()){
            return new DuplicateNicknameDto(true);
        }

        return new DuplicateNicknameDto(false);
    }

    public Member getMemberById(UUID id){
        Optional<Member> member = memberRepository.findById(id);

        if(member.isPresent()){
            Member curMember = member.get();

            return curMember;
        }

        throw new EntityNotFoundException();
    }
}
