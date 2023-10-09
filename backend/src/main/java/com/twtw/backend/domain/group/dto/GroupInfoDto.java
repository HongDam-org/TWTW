package com.twtw.backend.domain.group.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfoDto {
    private UUID id;
    private String name;
    private String groupImage;
}
