package com.twtw.backend.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequest {
    @NotBlank private String nickname;
    @NotBlank private String profileImage;
    @NotBlank private String deviceToken;
    private OAuthRequest oauthRequest;
}
