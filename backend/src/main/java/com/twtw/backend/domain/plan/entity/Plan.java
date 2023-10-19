package com.twtw.backend.domain.plan.entity;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.place.entity.Place;

import jakarta.persistence.*;

import lombok.AccessLevel;
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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Place place;

    @JoinColumn(name = "group_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Group group;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.PERSIST)
    private Set<PlanMember> planMembers = new HashSet<>();

    public Plan(Member member, Place place, Group group) {
        addMember(member);
        addPlace(place);
        addGroup(group);
    }

    public void addMember(final Member member) {
        this.planMembers.add(new PlanMember(this, member));
    }

    public void addPlace(final Place place) {
        this.place = place;
    }

    public void addGroup(Group group) {
        this.group = group;
        group.getGroupPlans().add(this);
    }
}
