package com.twtw.backend.support.exclude;

import com.twtw.backend.domain.notification.messagequeue.FcmProducer;
import com.twtw.backend.support.database.ResetDatabase;
import com.twtw.backend.support.testcontainer.ContainerTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@Import(ResetDatabase.class)
@ContextConfiguration(initializers = ContainerTest.class)
public abstract class ExcludeTest {

    @MockBean private FcmProducer fcmProducer;
    @Autowired private ResetDatabase resetDatabase;

    @BeforeEach
    void setUp() {
        doNothing().when(fcmProducer).sendNotification(any());
        resetDatabase.reset();
    }
}
