package com.twtw.backend.fixture.plan;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.domain.plan.entity.PlanMember;
import com.twtw.backend.fixture.member.MemberEntityFixture;

public enum PlanMemberEntityFixture {
    FIRST_PLAN_MEMBER(MemberEntityFixture.FIRST_MEMBER.toEntity()),
    SECOND_PLAN_MEMBER(MemberEntityFixture.SECOND_MEMBER.toEntity());

    private Member member;

    PlanMemberEntityFixture(Member member) {
        this.member = member;
    }

    public PlanMember toEntity(final Plan plan) {
        return new PlanMember(plan, this.member);
    }
}
