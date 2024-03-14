package com.twtw.backend.domain;

import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;
import com.twtw.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestController {

    private final MemberRepository repository;

//    @PostConstruct
    public void test() {
        if (repository.count() != 0) {
            return;
        }
        final List<Member> members = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            if (i % 2 == 0) {
                members.add(Member.builder().nickname("people_" + UUID.randomUUID())
                        .oauthInfo(new OAuth2Info(UUID.randomUUID().toString(), AuthType.APPLE)).build());
                continue;
            }
            members.add(Member.builder().nickname(UUID.randomUUID().toString())
                    .oauthInfo(new OAuth2Info(UUID.randomUUID().toString(), AuthType.KAKAO)).build());
        }
        repository.saveAll(members);
    }
}
