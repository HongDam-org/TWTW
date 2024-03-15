package com.twtw.backend.domain.member.repository;

import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaMemberRepository extends JpaRepository<Member, UUID>, MemberRepository {

    @Query(
            value =
                    "SELECT * FROM member m WHERE MATCH (m.nickname) AGAINST(:nickname IN BOOLEAN"
                            + " MODE)",
            nativeQuery = true)
    List<Member> findAllByNickname(@Param("nickname") String nickname);

    @Query(
            "SELECT m FROM Member m WHERE m.oauthInfo.clientId = :oAuthId AND"
                    + " m.oauthInfo.authType = :authType")
    Optional<Member> findByOAuthIdAndAuthType(
            @Param("oAuthId") String oAuthId, @Param("authType") AuthType authType);

    @Query("SELECT m FROM Member m WHERE m.id in :friendMemberIds")
    List<Member> findAllByIds(@Param("friendMemberIds") final List<UUID> friendMemberIds);
}
