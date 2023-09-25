package com.twtw.backend.domain.member.mapper;

import com.twtw.backend.domain.member.dto.request.MemberSaveRequest;
import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;

import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toMemberEntity(MemberSaveRequest request) {
        Member member =
                new Member(
                        request.getNickname(),
                        request.getProfileImage(),
                        request.getRole());
        return member;
    }

    public OAuth2Info toOAuthInfo(String clientId, AuthType type) {
        OAuth2Info info = new OAuth2Info(clientId, type);

        return info;
    }
}
