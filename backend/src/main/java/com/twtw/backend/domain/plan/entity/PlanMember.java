package com.twtw.backend.domain.plan.entity;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.global.audit.AuditListener;
import com.twtw.backend.global.audit.Auditable;
import com.twtw.backend.global.audit.BaseTime;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.Where;

import java.util.UUID;

@Getter
@Entity
@Where(clause = "deleted_at is null")
@EntityListeners(AuditListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanMember implements Auditable {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @JoinColumn(columnDefinition = "BINARY(16)")
    @ManyToOne(fetch = FetchType.LAZY)
    private Plan plan;

    @JoinColumn(columnDefinition = "BINARY(16)")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private PlanInviteCode planInviteCode;

    private Boolean isPlanMaker;

    @Setter
    @Embedded
    @Column(nullable = false)
    private BaseTime baseTime;

    @Builder
    public PlanMember(final Plan plan, final Member member, final Boolean isPlanMaker) {
        this.plan = plan;
        this.member = member;
        this.isPlanMaker = isPlanMaker;
        this.planInviteCode = PlanInviteCode.REQUESTED;
    }

    public boolean isSameMember(final Member member) {
        return this.member.equals(member);
    }

    public void updateToPlanMaker() {
        this.isPlanMaker = true;
    }

    public boolean isAccepted() {
        return this.planInviteCode == PlanInviteCode.ACCEPTED;
    }

    public void acceptInvite() {
        this.planInviteCode = PlanInviteCode.ACCEPTED;
    }

    public String getDeviceTokenValue() {
        return this.member.getDeviceTokenValue();
    }
}
