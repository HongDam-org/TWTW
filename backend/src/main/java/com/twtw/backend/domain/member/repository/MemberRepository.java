package com.twtw.backend.domain.member.repository;

import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findById(UUID uuid);

    Optional<Member> findByNickname(String nickName);

    @Query(
            "SELECT m FROM Member m WHERE m.oauthInfo.clientId = :oAuthId AND"
                    + " m.oauthInfo.authType = :authType")
    Optional<Member> findByOAuthIdAndAuthType(String oAuthId, AuthType authType);
}
