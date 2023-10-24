package com.twtw.backend.domain.member.mapper;

import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.dto.response.MemberResponse;
import com.twtw.backend.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toMemberEntity(MemberSaveRequest request) {
        Member member = new Member(
                request.getNickname(),
                request.getProfileImage(),
                request.getOauthRequest().getToken(),
                request.getOauthRequest().getAuthType());
        return member;
    }

    public MemberResponse toMemberResponse(Member member) {
        return new MemberResponse(member.getId(), member.getNickname());
    }
}
