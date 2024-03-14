package com.twtw.backend.support.stub;

import com.twtw.backend.domain.friend.entity.Friend;
import com.twtw.backend.domain.friend.repository.FriendCommandRepository;
import com.twtw.backend.domain.friend.repository.FriendQueryRepository;
import com.twtw.backend.domain.group.repository.GroupRepository;
import com.twtw.backend.domain.member.repository.MemberRepository;
import com.twtw.backend.domain.member.repository.RefreshTokenRepository;
import com.twtw.backend.domain.plan.repository.PlanRepository;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@TestConfiguration
public class StubConfig {

    private final Map<UUID, Friend> map = new HashMap<>();

    @Bean
    @Primary
    public FriendQueryRepository stubFriendQueryRepository() {
        return new StubFriendQueryRepository(map);
    }

    @Bean
    @Primary
    public FriendCommandRepository stubFriendCommandRepository() {
        return new StubFriendCommandRepository(map);
    }

    @Bean
    @Primary
    public RefreshTokenRepository refreshTokenRepository() {
        return new StubRefreshTokenRepository();
    }

    @Bean
    @Primary
    public GroupRepository groupRepository() {
        return new StubGroupRepository();
    }

    @Bean
    @Primary
    public MemberRepository memberRepository() {
        return new StubMemberRepository();
    }

    @Bean
    @Primary
    public PlanRepository planRepository() {
        return new StubPlanRepository();
    }
}
