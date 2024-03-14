package com.twtw.backend;

import com.twtw.backend.support.testcontainer.ContainerTestConfig;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers = ContainerTestConfig.class)
class BackendApplicationTests {

    @Test
    void contextLoads() {}
}
