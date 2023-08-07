package com.twtw.backend.module.member.repository;


import com.twtw.backend.module.member.entity.AuthType;
import com.twtw.backend.module.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findById(UUID uuid);

    @Query("SELECT m FROM Member m WHERE m.oAuth2Info.clientId = :OAuthId AND m.oAuth2Info.authType = :authType")
    Optional<Member> findByOAuthIdAndAuthType(String OAuthId, AuthType authType);
}
