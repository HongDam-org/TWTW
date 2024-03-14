package com.twtw.backend.domain.plan.repository;

import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.plan.entity.Plan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaPlanRepository extends JpaRepository<Plan, UUID>, PlanRepository {

    @Query(
            "select p from Plan p join fetch p.planMembers pm join fetch pm.member m where m ="
                    + " :member")
    List<Plan> findAllByMember(@Param("member") Member member);
}
