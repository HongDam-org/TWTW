package com.twtw.backend.domain.member.service;

import com.twtw.backend.domain.member.dto.response.DuplicateNicknameResponse;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.mapper.MemberMapper;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.global.exception.EntityNotFoundException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final AuthService authService;
    private final MemberMapper memberMapper;

    public MemberService(
            MemberRepository memberRepository, AuthService authService, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.authService = authService;
        this.memberMapper = memberMapper;
    }

    @Transactional(readOnly = true)
    public DuplicateNicknameResponse duplicateNickname(String nickname) {

        return new DuplicateNicknameResponse(memberRepository.existsByNickname(nickname));
    }

    public Member getMemberById(UUID id) {
        return memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public String getMemberIdValue() {
        return authService.getMemberIdValue();
    }

    @CacheEvict(
            value = "getMemberByNicknameWithCache",
            key = "'getMemberWithCache'.concat(#root.target.getMemberIdValue()).concat(#nickname)",
            cacheManager = "cacheManager")
    @Transactional(readOnly = true)
    public List<MemberResponse> getMemberByNickname(final String nickname) {
        final List<Member> members =
                memberRepository.findAllByNicknameContainingIgnoreCase(nickname);
        members.removeIf(authService.getMemberByJwt()::equals);
        return getResponsesByMembers(members);
    }

    @Cacheable(
            value = "getMemberByNicknameWithCache",
            key = "'getMemberWithCache'.concat(#root.target.getMemberIdValue()).concat(#nickname)",
            cacheManager = "cacheManager",
            unless = "#result.size() <= 0")
    @Transactional(readOnly = true)
    public List<MemberResponse> getMemberByNicknameWithCache(final String nickname) {
        final List<Member> members =
                memberRepository.findAllByNicknameContainingIgnoreCase(nickname);
        members.removeIf(authService.getMemberByJwt()::equals);
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

    @CacheEvict(
            value = "getMemberByIdWithCache",
            key = "'getMemberByIdWithCache'.concat(#root.target.getMemberIdValue())",
            cacheManager = "cacheManager")
    @Transactional(readOnly = true)
    public MemberResponse getMemberId() {
        return getResponseByMember(authService.getMemberByJwt());
    }

    @Cacheable(
            value = "getMemberByIdWithCache",
            key = "'getMemberByIdWithCache'.concat(#root.target.getMemberIdValue())",
            cacheManager = "cacheManager")
    public MemberResponse getMemberIdWithCache() {
        return getResponseByMember(authService.getMemberByJwt());
    }
}
