package com.twtw.backend;

import com.twtw.backend.support.testcontainer.ContainerTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = ContainerTest.class)
class BackendApplicationTests {

    @Test
    void contextLoads() {}
}
