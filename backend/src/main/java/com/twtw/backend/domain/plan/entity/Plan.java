package com.twtw.backend.domain.plan.entity;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;
import com.twtw.backend.global.audit.SoftDelete;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@SoftDelete
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plan implements Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @JoinColumn(columnDefinition = "BINARY(16)")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Place place;

    @JoinColumn(name = "group_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private Group group;

    @OneToMany(
            mappedBy = "plan",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private Set<PlanMember> planMembers = new HashSet<>();

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    public Plan(Member member, Place place, Group group) {
        addMember(member);
        addPlace(place);
        addGroup(group);
    }

    public void addMember(final Member member) {
        this.planMembers.add(new PlanMember(this, member));
    }

    public void deleteMember(final Member member) {
        this.planMembers.remove(member);
    }

    public void addPlace(final Place place) {
        this.place = place;
    }

    public void addGroup(Group group) {
        this.group = group;
        group.getGroupPlans().add(this);
    }
}
