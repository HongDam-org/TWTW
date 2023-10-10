package com.twtw.backend.domain.member.entity;

import com.twtw.backend.domain.group.entity.GroupMember;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String nickname;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded private OAuth2Info oAuth2Info;

    @OneToMany(mappedBy = "member",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<GroupMember> groupMembers = new ArrayList<>();

    public Member(String nickname, String profileImage) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = Role.ROLE_USER;
    }

    public void updateOAuth(OAuth2Info oAuth2Info) {
        this.oAuth2Info = oAuth2Info;
    }
}
