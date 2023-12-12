package com.twtw.backend.domain.member.entity;

import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Where(clause = "deleted_at is null")
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(unique = true)
    private String nickname;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded private OAuth2Info oauthInfo;

    @OneToMany(mappedBy = "member")
    private List<GroupMember> groupMembers = new ArrayList<>();

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    @Builder
    public Member(String nickname, String profileImage, OAuth2Info oauthInfo) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = Role.ROLE_USER;
        this.oauthInfo = oauthInfo;
    }

    public void addGroupMember(final GroupMember groupMember) {
        this.groupMembers.add(groupMember);
    }

    public boolean hasNoGroupMember() {
        return this.groupMembers.isEmpty();
    }

    public void updateProfileImage(final String profileImage) {
        this.profileImage = profileImage;
    }
}
