package com.twtw.backend.domain.group.entity;

import com.twtw.backend.domain.member.entity.Member;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupMember {
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

    public void changeGroupCode(GroupInviteCode groupInviteCode){
        this.groupInviteCode = groupInviteCode;
    }

}
