package com.twtw.backend.group;

import com.twtw.backend.config.database.QuerydslConfig;
import com.twtw.backend.domain.group.entity.Group;
import com.twtw.backend.domain.group.repository.GroupRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@ActiveProfiles("test")
@Import(QuerydslConfig.class)
public class GroupTest {

    @Autowired private GroupRepository groupRepository;
    @Test
    @Transactional
    void saveGroup() {

    }
}
