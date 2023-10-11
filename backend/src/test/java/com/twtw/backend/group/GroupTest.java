package com.twtw.backend.group;

import com.twtw.backend.config.database.QuerydslConfig;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.entity.GroupMember;
import com.twtw.backend.domain.group.repository.GroupMemberRepository;
import com.twtw.backend.domain.group.repository.GroupRepository;
import com.twtw.backend.domain.member.entity.AuthType;
import com.twtw.backend.domain.member.entity.Member;
import com.twtw.backend.domain.member.entity.OAuth2Info;
import com.twtw.backend.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
public class GroupTest {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach(){
        Member member = new Member("jinjooone","22222");
        OAuth2Info info = new OAuth2Info("ADMIN",AuthType.KAKAO);
        member.updateOAuth(info);

        memberRepository.save(member);

        Group group = new Group("AAA","1111");
        groupRepository.save(group);
    }

    @Test
    @Transactional
    void saveGroup(){
        Member member = memberRepository.findByOAuthIdAndAuthType("ADMIN",AuthType.KAKAO).orElseThrow(EntityNotFoundException::new);
        Group group = new Group("HDJ","1111");
        GroupMember groupMember = new GroupMember(group,member);
        Group regroup = groupRepository.save(group);
    }

    @Test
    @Transactional
    void joinGroup(){

    }

}
