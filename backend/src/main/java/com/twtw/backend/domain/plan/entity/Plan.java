package com.twtw.backend.domain.plan.entity;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.place.entity.CategoryGroupCode;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;
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
import org.hibernate.annotations.Where;

@Getter
@Entity
@Where(clause = "deleted_at is null")
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
        this.planMembers.add(new PlanMember(this, member, true));
        organizePlace(place);
        organizeGroup(group);
    }

    public void addMember(final Member member) {
        this.planMembers.add(new PlanMember(this, member, false));
    }

    public void deleteMember(final Member member) {
        this.planMembers.removeIf(planMember -> planMember.hasSameMember(member));

        if (hasNoPlanMaker()) {
            this.planMembers.stream().findFirst().ifPresent(PlanMember::updateToPlanMaker);
        }
    }

    private boolean hasNoPlanMaker() {
        return this.planMembers.stream().noneMatch(PlanMember::getIsPlanMaker);
    }

    private void organizePlace(final Place place) {
        this.place = place;
    }

    public void organizeGroup(final Group group) {
        this.group = group;
        this.group.addPlan(this);
    }

    public void updatePlace(
            final String placeName,
            final String placeUrl,
            final CategoryGroupCode categoryGroupCode,
            final String roadAddressName,
            final Double longitude,
            final Double latitude) {
        this.place.update(placeName, placeUrl, categoryGroupCode, roadAddressName, longitude, latitude);
    }

    public void updateMemberLocation(final Member member, final Double longitude, final Double latitude) {
        this.group.updateMemberLocation(member, longitude, latitude);
    }
}
