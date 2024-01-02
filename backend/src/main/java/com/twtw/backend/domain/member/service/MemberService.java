package com.twtw.backend.domain.member.service;

import com.twtw.backend.domain.member.dto.response.DuplicateNicknameResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.mapper.MemberMapper;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.global.exception.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;

    private final MemberMapper memberMapper;

    public MemberService(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    public DuplicateNicknameResponse duplicateNickname(String nickname) {

        return new DuplicateNicknameResponse(memberRepository.existsByNickname(nickname));
    }

    public Member getMemberById(UUID id) {
        return memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public List<MemberResponse> getMemberByNickname(String nickname) {
        final List<Member> members = memberRepository.findAllByNicknameContainingIgnoreCase(nickname);
        return getResponsesByMembers(members);
    }

    public MemberResponse getResponseByMember(Member member) {
        return memberMapper.toMemberResponse(member);
    }

    public List<MemberResponse> getResponsesByMembers(final List<Member> members) {
        return memberMapper.toMemberResponses(members);
    }

    public List<MemberResponse> getMemberResponses(final Plan plan) {
        return memberMapper.toMemberResponses(plan.getPlanMembers());
    }

    public List<Member> getMembersByIds(final List<UUID> friendMemberIds) {
        return memberRepository.findAllById(friendMemberIds);
    }
}
