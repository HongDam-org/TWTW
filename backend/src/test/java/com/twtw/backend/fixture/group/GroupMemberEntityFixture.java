package com.twtw.backend.fixture.group;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupInviteCode;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
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

    private final Group group;
    private final Member member;
    private final Boolean share;
    private final GroupInviteCode groupInviteCode;

    public GroupMember toEntity() {
        return new GroupMember(this.group, this.member);
    }
}
