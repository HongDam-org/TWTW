package com.twtw.backend.module.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id",columnDefinition = "BINARY(16)")
    private UUID id;

    private String nickname;

    private String profileImage;

    private String phoneNumber;

    @Embedded
    private OAuth2Info oAuth2Info;

}
