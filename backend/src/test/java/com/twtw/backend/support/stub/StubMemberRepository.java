package com.twtw.backend.support.stub;

import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;
import com.twtw.backend.domain.member.repository.MemberRepository;

import java.util.*;

public class StubMemberRepository implements MemberRepository {

    private final Map<UUID, Member> map = new HashMap<>();

    @Override
    public List<Member> findAllByNickname(final String nickname) {
        return map.values().stream()
                .filter(
                        member ->
                                member.getNickname().toUpperCase().contains(nickname.toUpperCase()))
                .toList();
    }

    @Override
    public List<Member> findAllByNicknameContainingIgnoreCase(final String nickname) {
        return map.values().stream()
                .filter(
                        member ->
                                member.getNickname().toUpperCase().contains(nickname.toUpperCase()))
                .toList();
    }

    @Override
    public Optional<Member> findByOAuthIdAndAuthType(
            final String oAuthId, final AuthType authType) {
        return map.values().stream()
                .filter(
                        member -> {
                            final OAuth2Info oauthInfo = member.getOauthInfo();
                            return oauthInfo.getClientId().equals(oAuthId)
                                    && oauthInfo.getAuthType().equals(authType);
                        })
                .findFirst();
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return map.values().stream().anyMatch(member -> member.getNickname().equals(nickname));
    }

    @Override
    public Member save(final Member member) {
        map.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(final UUID id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public List<Member> findAllByIds(final List<UUID> friendMemberIds) {
        return map.values().stream()
                .filter(member -> friendMemberIds.contains(member.getId()))
                .toList();
    }

    @Override
    public void deleteById(final UUID memberId) {
        map.remove(memberId);
    }
}
