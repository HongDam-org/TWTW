package com.twtw.backend.domain.plan.entity;

import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.place.entity.CategoryGroupCode;
import com.twtw.backend.domain.place.entity.Place;
import com.twtw.backend.domain.plan.exception.PlanMakerNotExistsException;
import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Column(nullable = false)
    private String name;

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

    private LocalDateTime planDay;

    @Builder
    public Plan(String name, Member member, Place place, Group group, LocalDateTime planDay) {
        this.name = name;
        this.planMembers.add(new PlanMember(this, member, true));
        this.place = place;
        organizeGroup(group);
        this.planDay = planDay;
    }

    public void addMember(final Member member) {
        if (hasSameMember(member)) {
            return;
        }
        this.planMembers.add(new PlanMember(this, member, false));
    }

    private boolean hasSameMember(final Member member) {
        return this.planMembers.stream().anyMatch(planMember -> planMember.isSameMember(member));
    }

    public Set<PlanMember> getPlanMembers() {
        return this.planMembers.stream().filter(PlanMember::isAccepted).collect(Collectors.toSet());
    }

    public void deleteMember(final Member member) {
        this.planMembers.removeIf(planMember -> planMember.isSameMember(member));

        if (hasNoPlanMaker()) {
            this.planMembers.stream().findFirst().ifPresent(PlanMember::updateToPlanMaker);
        }
    }

    private boolean hasNoPlanMaker() {
        return this.planMembers.stream().noneMatch(PlanMember::getIsPlanMaker);
    }

    private void organizeGroup(final Group group) {
        this.group = group;
        this.group.addPlan(this);
    }

    public void updatePlace(
            final String name,
            final LocalDateTime planDay,
            final String placeName,
            final String placeUrl,
            final CategoryGroupCode categoryGroupCode,
            final String roadAddressName,
            final Double longitude,
            final Double latitude) {
        this.name = name;
        this.planDay = planDay;
        this.place.update(
                placeName, placeUrl, categoryGroupCode, roadAddressName, longitude, latitude);
    }

    public void updateMemberLocation(
            final Member member, final Double longitude, final Double latitude) {
        this.group.updateMemberLocation(member, longitude, latitude);
    }

    public void updatePlanDay(LocalDateTime changeDay) {
        this.planDay = changeDay;
    }

    public UUID getPlanMakerId() {
        return this.planMembers.stream()
                .filter(PlanMember::getIsPlanMaker)
                .findFirst()
                .orElseThrow(PlanMakerNotExistsException::new)
                .getMember()
                .getId();
    }

    public void acceptInvite(final Member member) {
        this.planMembers.stream()
                .filter(planMember -> planMember.isSameMember(member))
                .findFirst()
                .ifPresent(PlanMember::acceptInvite);
    }

    public void deleteInvite(final Member member) {
        this.planMembers.removeIf(planMember -> planMember.isSameMember(member));
    }

    public List<Member> getNotJoinedMembers() {
        return this.group.getGroupMembers().stream()
                .map(GroupMember::getMember)
                .filter(member -> !hasSameMember(member))
                .toList();
    }

    public void addMembers(final List<Member> membersByIds) {
        membersByIds.forEach(this::addMember);
    }
}
