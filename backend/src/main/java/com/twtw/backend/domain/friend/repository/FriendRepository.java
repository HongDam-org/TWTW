package com.twtw.backend.domain.friend.repository;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FriendRepository extends JpaRepository<Friend, UUID>, FriendQueryRepository {

    @Query(value = "SELECT * FROM Friend WHERE friendStatus = 'ACCEPTED' AND (toMember = :member AND MATCH(toMember.nickname) AGAINST (:nickname IN BOOLEAN MODE) OR fromMember = :member AND MATCH (fromMember.nickname) AGAINST (:nickname IN BOOLEAN MODE))", nativeQuery = true)
    List<Friend> findByMemberAndMemberNickname(@Param("member") final Member member, @Param("nickname") final String nickname);
}
