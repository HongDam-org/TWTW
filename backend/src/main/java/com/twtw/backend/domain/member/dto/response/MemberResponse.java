package com.twtw.backend.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class MemberResponse {
    private UUID memberId;
    private String nickname;
    private String profileImage;
}
