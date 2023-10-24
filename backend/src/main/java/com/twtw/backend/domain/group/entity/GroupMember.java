package com.twtw.backend.domain.group.entity;

import com.twtw.backend.domain.member.entity.Member;

import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;
import com.twtw.backend.global.audit.SoftDelete;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
import lombok.Setter;

@Entity
@Getter
@SoftDelete
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
    }

    public void changeShare() {
        this.share = !this.share;
    }
}
