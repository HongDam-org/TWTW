package com.twtw.backend.support.stub;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.plan.entity.Plan;
import com.twtw.backend.domain.plan.repository.PlanRepository;

import java.util.*;

public class StubPlanRepository implements PlanRepository {

    private final Map<UUID, Plan> map = new HashMap<>();

    @Override
    public List<Plan> findAllByMember(final Member member) {
        return map.values().stream()
                .filter(
                        plan ->
                                plan.getPlanMembers().stream()
                                        .anyMatch(planMember -> planMember.isSameMember(member)))
                .toList();
    }

    @Override
    public Plan save(final Plan plan) {
        map.put(plan.getId(), plan);
        return plan;
    }

    @Override
    public void deleteById(final UUID id) {
        map.remove(id);
    }

    @Override
    public Optional<Plan> findById(final UUID id) {
        return Optional.ofNullable(map.get(id));
    }
}
