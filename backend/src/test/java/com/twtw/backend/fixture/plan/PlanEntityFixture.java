package com.twtw.backend.fixture.plan;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.fixture.member.MemberEntityFixture;
import com.twtw.backend.fixture.place.PlaceEntityFixture;

import java.util.UUID;

public enum PlanEntityFixture {
    FIRST_PLACE(
            MemberEntityFixture.LOGIN_MEMBER.toEntity(),
            PlaceEntityFixture.FIRST_PLACE.toEntity(),
            new Group("1", "2", UUID.randomUUID())),
    SECOND_PLACE(
            MemberEntityFixture.FIRST_MEMBER.toEntity(),
            PlaceEntityFixture.SECOND_PLACE.toEntity(),
            new Group("3", "4", UUID.randomUUID()));

    private final Member member;
    private final Place place;
    private final Group group;

    PlanEntityFixture(final Member member, final Place place, final Group group) {
        this.member = member;
        this.place = place;
        this.group = group;
    }

    public Plan toEntity() {
        return new Plan(member, place, group);
    }
}
