package com.twtw.backend.fixture.plan;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.domain.plan.entity.PlanMember;
import com.twtw.backend.fixture.member.MemberEntityFixture;

public enum PlanMemberEntityFixture {

    FIRST_PLAN_MEMBER(PlanEntityFixture.FIRST_PLACE.toEntity(), MemberEntityFixture.FIRST_MEMBER.toEntity()),
    SECOND_PLAN_MEMBER(PlanEntityFixture.SECOND_PLACE.toEntity(), MemberEntityFixture.SECOND_MEMBER.toEntity());

    private Plan plan;
    private Member member;

    PlanMemberEntityFixture(Plan plan,Member member){
        this.plan = plan;
        this.member = member;
    }

    public PlanMember toEntity(){
        return new PlanMember(this.plan,this.member);
    }
}
