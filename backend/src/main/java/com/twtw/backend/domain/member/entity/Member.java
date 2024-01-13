package com.twtw.backend.domain.member.entity;

import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.Where;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@EqualsAndHashCode(of = "nickname")
@Where(clause = "deleted_at is null")
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String profileImage;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Embedded private OAuth2Info oauthInfo;

    @OneToMany(mappedBy = "member")
    private List<GroupMember> groupMembers = new ArrayList<>();

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn
    private DeviceToken deviceToken;

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    @Builder
    public Member(String nickname, String profileImage, OAuth2Info oauthInfo, String deviceToken) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.role = Role.ROLE_USER;
        this.oauthInfo = oauthInfo;
        updateDeviceToken(new DeviceToken(deviceToken));
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

    public void updateDeviceToken(final DeviceToken deviceToken) {
        this.deviceToken = deviceToken;
        deviceToken.organizeMember(this);
    }

    public String getDeviceTokenValue() {
        return this.deviceToken.getDeviceToken();
    }

    public List<GroupMember> getGroupMembers() {
        return this.groupMembers.stream().filter(GroupMember::isAccepted).toList();
    }

    public void removeGroupMember(final GroupMember groupMember) {
        this.groupMembers.remove(groupMember);
    }
}
