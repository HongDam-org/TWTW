package com.twtw.backend.domain.group.entity;

import com.twtw.backend.domain.member.entity.Member;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Getter
@Where(clause = "deleted_at is null")
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember implements Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean share;

    @Enumerated(EnumType.STRING)
    private GroupInviteCode groupInviteCode;

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    public GroupMember(Group group, Member member) {
        this.group = group;
        this.member = member;
        this.share = true;
        member.addGroupMember(this);
        this.groupInviteCode = GroupInviteCode.REQUESTED;
    }

    public void share() {
        this.share = true;
    }

    public void unShare() {
        this.share = false;
    }

    public void acceptInvite() {
        this.groupInviteCode = GroupInviteCode.ACCEPTED;
    }

    public UUID getGroupId() {
        return this.group.getId();
    }
}
