package com.twtw.backend.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSaveRequest {
    @NotBlank
    private String nickname;
    @NotNull
    private String profileImage;

    private OAuthRequest oauthRequest;
}
