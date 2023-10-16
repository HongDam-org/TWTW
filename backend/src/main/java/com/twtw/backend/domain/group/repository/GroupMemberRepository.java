package com.twtw.backend.domain.group.repository;

import com.twtw.backend.domain.group.entity.GroupMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface GroupMemberRepository extends JpaRepository<GroupMember, UUID> {

    @Query("SELECT gm from GroupMember gm WHERE gm.group.id = :groupID AND " +
            "gm.member.id = :memberID")
    Optional<GroupMember> findByGroupIdAndMemberId(UUID groupId,UUID memberId);
}
