package com.twtw.backend.domain.member.dto.request;

import com.twtw.backend.domain.member.entity.Role;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequest {
    private String nickname;

    private String profileImage;

    private String phoneNumber;

    private Role role;

    private OAuthRequest oauthRequest;
}
