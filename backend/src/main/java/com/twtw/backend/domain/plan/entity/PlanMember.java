package com.twtw.backend.domain.plan.entity;

import com.twtw.backend.domain.member.entity.Member;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlanMember {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Plan plan;

    @JoinColumn
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public PlanMember(final Plan plan, final Member member) {
        this.plan = plan;
        this.member = member;
    }
}
