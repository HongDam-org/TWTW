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
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

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
        group.getGroupMembers().add(this);
        member.getGroupMembers().add(this);
        this.groupInviteCode = GroupInviteCode.REQUESTED;
    }

    public void changeShare() {
        this.share = !this.share;
    }

    public void changeGroupCode(GroupInviteCode groupInviteCode) {
        this.groupInviteCode = groupInviteCode;
    }
}
