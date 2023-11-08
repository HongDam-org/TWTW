package com.twtw.backend.fixture.group;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupInviteCode;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.fixture.member.MemberEntityFixture;

public enum GroupMemberEntityFixture {
    FIRST_GROUP_MEMBER(
            GroupEntityFixture.HDJ_GROUP.toEntity(),
            MemberEntityFixture.FIRST_MEMBER.toEntity(),
            true,
            GroupInviteCode.REQUESTED),
    SECOND_GROUP_MEMBER(
            GroupEntityFixture.HDJ_GROUP.toEntity(),
            MemberEntityFixture.SECOND_MEMBER.toEntity(),
            true,
            GroupInviteCode.ACCEPTED),
    THIRD_GROUP_MEMBER(
            GroupEntityFixture.BTS_GROUP.toEntity(),
            MemberEntityFixture.LOGIN_MEMBER.toEntity(),
            true,
            GroupInviteCode.ACCEPTED);

    private Group group;
    private Member member;
    private Boolean share;
    private GroupInviteCode groupInviteCode;

    GroupMemberEntityFixture(
            final Group group,
            final Member member,
            final Boolean share,
            final GroupInviteCode groupInviteCode) {
        this.group = group;
        this.member = member;
        this.share = share;
        this.groupInviteCode = groupInviteCode;
    }

    public GroupMember toEntity() {
        return new GroupMember(this.group, this.member);
    }
}
