package com.twtw.backend.fixture.plan;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.fixture.place.PlaceEntityFixture;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PlanEntityFixture {
    FIRST_PLACE(PlaceEntityFixture.FIRST_PLACE.toEntity()),
    SECOND_PLACE(PlaceEntityFixture.SECOND_PLACE.toEntity());

    private final Place place;

    public Plan toEntity(final Member member, final Group group) {
        return new Plan(member, place, group);
    }
}
