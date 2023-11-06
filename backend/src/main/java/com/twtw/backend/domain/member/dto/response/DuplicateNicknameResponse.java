package com.twtw.backend.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DuplicateNicknameResponse {
    private Boolean isPresent;
}
