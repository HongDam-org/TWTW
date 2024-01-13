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

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Where(clause = "deleted_at is null and plan_invite_code != 'EXPIRED'")
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
        initializeIsPlanMaker(isPlanMaker);
    }

    private void initializeIsPlanMaker(final Boolean isPlanMaker) {
        if (isPlanMaker) {
            this.planInviteCode = PlanInviteCode.ACCEPTED;
            return;
        }
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
        if (isRequestNotExpired()) {
            this.planInviteCode = PlanInviteCode.ACCEPTED;
            return;
        }
        remove();
    }

    private void remove() {
        this.planInviteCode = PlanInviteCode.EXPIRED;
        this.plan.remove(this);
    }

    public void checkExpire() {
        if (isRequestNotExpired()) {
            return;
        }
        remove();
    }

    public String getDeviceTokenValue() {
        return this.member.getDeviceTokenValue();
    }

    private boolean isRequestNotExpired() {
        return this.baseTime == null
                || (this.planInviteCode == PlanInviteCode.REQUESTED
                        && this.baseTime
                                .getCreatedAt()
                                .isAfter(LocalDateTime.now().minusMinutes(30L)));
    }
}
