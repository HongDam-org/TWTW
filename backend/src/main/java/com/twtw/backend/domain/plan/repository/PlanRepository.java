package com.twtw.backend.domain.plan.repository;

import com.twtw.backend.domain.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {}
