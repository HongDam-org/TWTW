package com.twtw.backend.domain.group.repository;

import com.twtw.backend.domain.group.entity.GroupMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;
import java.util.UUID;

public interface GroupMemberRepository extends JpaRepository<GroupMember, UUID> {

    @Query(
            "SELECT gm from GroupMember gm WHERE gm.group.id = :groupId AND "
                    + "gm.member.id = :memberId")
    Optional<GroupMember> findByGroupIdAndMemberId(@Param("groupId") UUID groupId,@Param("memberId") UUID memberId);
}
