package com.twtw.backend.domain.member.entity;

import com.twtw.backend.domain.group.entity.GroupMember;

import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;
import com.twtw.backend.global.audit.SoftDelete;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Setter;

@Entity
@Getter
@SoftDelete
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    private String nickname;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded private OAuth2Info oAuth2Info;

    @OneToMany(mappedBy = "member")
    private List<GroupMember> groupMembers = new ArrayList<>();

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    public Member(String nickname, String profileImage, String clientId, AuthType authType) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = Role.ROLE_USER;
        this.oAuth2Info = new OAuth2Info(clientId, authType);
    }
}
