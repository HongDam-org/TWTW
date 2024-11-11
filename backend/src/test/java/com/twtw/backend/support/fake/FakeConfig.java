package com.twtw.backend.support.fake;

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
public class FakeConfig {

    private final Map<UUID, Friend> map = new HashMap<>();

    @Bean
    @Primary
    public FriendQueryRepository fakeFriendQueryRepository() {
        return new FakeFriendQueryRepository(map);
    }

    @Bean
    @Primary
    public FriendCommandRepository fakeFriendCommandRepository() {
        return new FakeFriendCommandRepository(map);
    }

    @Bean
    @Primary
    public RefreshTokenRepository fakeRefreshTokenRepository() {
        return new FakeRefreshTokenRepository();
    }

    @Bean
    @Primary
    public GroupRepository fakeGroupRepository() {
        return new FakeGroupRepository();
    }

    @Bean
    @Primary
    public MemberRepository fakeMemberRepository() {
        return new FakeMemberRepository();
    }

    @Bean
    @Primary
    public PlanRepository fakePlanRepository() {
        return new FakePlanRepository();
    }
}
