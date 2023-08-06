package com.twtw.backend.module.member.repository;


import com.twtw.backend.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findById(UUID uuid);
}
