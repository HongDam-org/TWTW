package com.twtw.backend.module.member.dto.request;

import com.twtw.backend.module.member.entity.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MemberRequest {
    private String userEmail;

    private String nickname;

    private String profileImage;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Role role;
}
