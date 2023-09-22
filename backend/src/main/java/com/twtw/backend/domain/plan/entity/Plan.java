package com.twtw.backend.domain.plan.entity;

import com.twtw.backend.domain.member.entity.Member;

import com.twtw.backend.domain.place.entity.Place;
import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @JoinColumn(columnDefinition = "BINARY(16)")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Place place;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.PERSIST)
    private Set<PlanMember> planMembers = new HashSet<>();

    @Builder
    public Plan(final List<Member> members) {
        organizePlanMember(members);
    }

    private void organizePlanMember(final List<Member> members) {
        members.stream().map(member -> new PlanMember(this, member)).forEach(this.planMembers::add);
    }

    public void addPlace(final Place place) {
        this.place = place;
    }
}
