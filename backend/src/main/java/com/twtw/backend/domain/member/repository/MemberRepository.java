package com.twtw.backend.domain.member.repository;

import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository {
    List<Member> findAllByNickname(final String nickname);
    List<Member> findAllByNicknameContainingIgnoreCase(final String nickname);
    Optional<Member> findByOAuthIdAndAuthType(final String oAuthId, final AuthType authType);
    boolean existsByNickname(final String nickname);
    Member save(final Member member);
    Optional<Member> findById(final UUID id);
    List<Member> findAllByIds(final List<UUID> friendMemberIds);
    void deleteById(final UUID memberId);
}
