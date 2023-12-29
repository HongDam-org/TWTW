package com.twtw.backend.support.exclude;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import com.twtw.backend.config.rabbitmq.RabbitMQConfig;
import com.twtw.backend.domain.location.controller.LocationController;
import com.twtw.backend.domain.notification.messagequeue.FcmProducer;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.actuate.autoconfigure.amqp.RabbitHealthContributorAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public abstract class ExcludeTest {

    @MockBean private RabbitAutoConfiguration rabbitAutoConfiguration;

    @MockBean private RabbitMQConfig rabbitMQConfig;

    @MockBean private RabbitAdmin rabbitAdmin;

    @MockBean
    private RabbitHealthContributorAutoConfiguration rabbitHealthContributorAutoConfiguration;

    @MockBean private LocationController locationController;

    @MockBean private FcmProducer fcmProducer;

    @BeforeEach
    void setUp() {
        doNothing().when(fcmProducer).sendNotification(any());
    }
}
