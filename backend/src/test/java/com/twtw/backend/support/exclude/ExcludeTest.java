package com.twtw.backend.support.exclude;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.twtw.backend.domain.notification.messagequeue.FcmProducer;
import com.twtw.backend.support.testcontainer.ContainerTestConfig;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = ContainerTestConfig.class)
public abstract class ExcludeTest {

    @MockBean private FcmProducer fcmProducer;

    @BeforeEach
    void setUp() {
        doNothing().when(fcmProducer).sendNotification(any());
    }
}
