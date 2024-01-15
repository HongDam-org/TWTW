package com.twtw.backend.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class IdResponse {
    private UUID id;
}
