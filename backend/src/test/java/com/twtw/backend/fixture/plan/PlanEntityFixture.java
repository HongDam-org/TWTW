package com.twtw.backend.fixture.plan;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.fixture.place.PlaceEntityFixture;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public enum PlanEntityFixture {
    FIRST_PLACE("호캉스 모임", PlaceEntityFixture.FIRST_PLACE.toEntity()),
    SECOND_PLACE("친구 모임", PlaceEntityFixture.SECOND_PLACE.toEntity());

    private final String name;
    private final Place place;

    public Plan toEntity(final Member member, final Group group, final LocalDateTime planDay) {
        return new Plan(name, member, place, group, planDay);
    }
}
