package com.twtw.backend.support.testcontainer;

import com.redis.testcontainers.RedisContainer;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

public class ContainerTest implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final RedisContainer REDIS_CONTAINER =
            new RedisContainer(DockerImageName.parse("redis:latest"))
                    .withExposedPorts(6379);

    private static final RabbitMQContainer RABBIT_MQ_CONTAINER =
            new RabbitMQContainer(DockerImageName.parse("rabbitmq:3-management-alpine"))
                    .withExposedPorts(15672, 5672, 61613)
                    .withPluginsEnabled("rabbitmq_web_stomp");

    static {
        REDIS_CONTAINER.start();
        RABBIT_MQ_CONTAINER.start();
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.data.redis.host=" + REDIS_CONTAINER.getHost(),
                "spring.data.redis.port=" + REDIS_CONTAINER.getFirstMappedPort(),
                "spring.rabbitmq.host=" + RABBIT_MQ_CONTAINER.getHost(),
                "spring.rabbitmq.port=" + RABBIT_MQ_CONTAINER.getAmqpPort(),
                "spring.rabbitmq.username=guest",
                "spring.rabbitmq.password=guest"
        ).applyTo(applicationContext.getEnvironment());
    }
}