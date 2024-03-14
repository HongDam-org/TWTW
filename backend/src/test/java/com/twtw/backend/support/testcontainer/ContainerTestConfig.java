package com.twtw.backend.support.testcontainer;

import com.redis.testcontainers.RedisContainer;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

public class ContainerTestConfig
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final RedisContainer REDIS_CONTAINER =
            new RedisContainer(DockerImageName.parse("redis:latest")).withExposedPorts(6379);

    private static final RabbitMQContainer RABBIT_MQ_CONTAINER =
            new RabbitMQContainer(DockerImageName.parse("rabbitmq:3-management-alpine"))
                    .withExposedPorts(15672, 5672, 61613)
                    .withPluginsEnabled("rabbitmq_web_stomp");

    private static final MySQLContainer MYSQL_CONTAINER =
            new MySQLContainer(DockerImageName.parse("mysql:8.0.22"))
                    .withDatabaseName("TWTW")
                    .withUsername("admin")
                    .withPassword("1234");

    static {
        REDIS_CONTAINER.start();
        RABBIT_MQ_CONTAINER.start();
        MYSQL_CONTAINER.start();
    }

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        final String jdbcUrl = MYSQL_CONTAINER.getJdbcUrl();
        final String username = MYSQL_CONTAINER.getUsername();
        final String password = MYSQL_CONTAINER.getPassword();

        TestPropertyValues.of(
                        "spring.data.redis.host=" + REDIS_CONTAINER.getHost(),
                        "spring.data.redis.port=" + REDIS_CONTAINER.getFirstMappedPort(),
                        "spring.rabbitmq.host=" + RABBIT_MQ_CONTAINER.getHost(),
                        "spring.rabbitmq.port=" + RABBIT_MQ_CONTAINER.getMappedPort(5672),
                        "spring.rabbitmq.stomp.port=" + RABBIT_MQ_CONTAINER.getMappedPort(61613),
                        "spring.rabbitmq.username=guest",
                        "spring.rabbitmq.password=guest",
                        "spring.datasource.url=" + jdbcUrl,
                        "spring.datasource.username=" + username,
                        "spring.datasource.password=" + password,
                        "spring.flyway.url=" + jdbcUrl,
                        "spring.flyway.user=" + username,
                        "spring.flyway.password=" + password)
                .applyTo(applicationContext.getEnvironment());
    }
}
