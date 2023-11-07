package com.twtw.backend.support.exclude;

import com.twtw.backend.config.rabbitmq.RabbitMQConfig;
import com.twtw.backend.domain.location.controller.LocationController;

import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public abstract class ExcludeTest {

    @MockBean private RabbitAutoConfiguration rabbitAutoConfiguration;

    @MockBean private RabbitMQConfig rabbitMQConfig;

    @MockBean private RabbitAdmin rabbitAdmin;

    @MockBean private LocationController locationController;
}
