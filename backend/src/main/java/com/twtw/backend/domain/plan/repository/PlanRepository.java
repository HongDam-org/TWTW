package com.twtw.backend.domain.plan.repository;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.plan.entity.Plan;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanRepository {
    List<Plan> findAllByMember(final Member member);

    Plan save(final Plan plan);

    void deleteById(final UUID id);

    Optional<Plan> findById(final UUID id);
}
