package com.twtw.backend.domain.member.repository;

import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {

    @Query(
            value =
                    "SELECT * FROM member WHERE MATCH(nickname) AGAINST(:nickname IN BOOLEAN MODE)", nativeQuery = true)
    List<Member> findAllByNicknameContainingIgnoreCase(@Param("nickname") String nickname);

    @Query(
            "SELECT m FROM Member m WHERE m.oauthInfo.clientId = :oAuthId AND"
                    + " m.oauthInfo.authType = :authType")
    Optional<Member> findByOAuthIdAndAuthType(
            @Param("oAuthId") String oAuthId, @Param("authType") AuthType authType);

    boolean existsByNickname(String nickname);
}
