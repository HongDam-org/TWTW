package com.twtw.backend.domain.group.repository;

import com.twtw.backend.domain.group.entity.GroupMember;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupMemberRepository extends JpaRepository<GroupMember, UUID> {}
