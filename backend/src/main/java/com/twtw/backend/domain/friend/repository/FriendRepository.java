package com.twtw.backend.domain.friend.repository;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, UUID> {
    Optional<Friend> findByFromMemberAndToMember(final Member fromMember, final Member toMember);
}
