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
            "SELECT m FROM Member m WHERE m.oAuth2Info.clientId = :oAuthId AND"
                    + " m.oAuth2Info.authType = :authType")
    Optional<Member> findByOAuthIdAndAuthType(String oAuthId, AuthType authType);


}
