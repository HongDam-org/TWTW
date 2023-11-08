package com.twtw.backend.fixture.plan;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.fixture.group.GroupEntityFixture;
import com.twtw.backend.fixture.place.PlaceEntityFixture;

public enum PlanEntityFixture {
    FIRST_PLACE(
            PlaceEntityFixture.FIRST_PLACE.toEntity(),
            GroupEntityFixture.HDJ_GROUP.toEntity()),
    SECOND_PLACE(
            PlaceEntityFixture.SECOND_PLACE.toEntity(),
            GroupEntityFixture.HDJ_GROUP.toEntity());

    private final Place place;
    private final Group group;

    PlanEntityFixture(final Place place, final Group group) {
        this.place = place;
        this.group = group;
    }

    public Plan toEntity(final Member member) {
        return new Plan(member, place, group);
    }
}
