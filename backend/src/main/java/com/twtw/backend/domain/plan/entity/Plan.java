package com.twtw.backend.domain.plan.entity;

import com.twtw.backend.domain.member.entity.Member;

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

    @Embedded private PlanPlace planPlace;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.PERSIST)
    private Set<PlanMember> planMembers = new HashSet<>();

    @Builder
    public Plan(final PlanPlace planPlace, final List<Member> members) {
        this.planPlace = planPlace;
        organizePlanMember(members);
    }

    public void organizePlanMember(final List<Member> members) {
        members.stream().map(member -> new PlanMember(this, member)).forEach(this.planMembers::add);
    }
}
