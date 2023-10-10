package com.twtw.backend;

import com.twtw.backend.domain.group.service.GroupService;
import com.twtw.backend.domain.member.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class BackendApplicationTests {
    @Test
    void contextLoads() {}
}
