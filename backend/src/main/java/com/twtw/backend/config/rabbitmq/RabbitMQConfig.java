package com.twtw.backend.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twtw.backend.global.constant.RabbitMQConstant;
import com.twtw.backend.global.properties.RabbitMQProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@EnableRabbit
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties rabbitMQProperties;
    private final ObjectMapper objectMapper;

    @Bean
    public Queue locationQueue() {
        return QueueBuilder.durable(RabbitMQConstant.LOCATION_QUEUE.getName())
                .deadLetterExchange(RabbitMQConstant.DEAD_LETTER_EXCHANGE.getName())
                .deadLetterRoutingKey(RabbitMQConstant.DEAD_LETTER_ROUTING_KEY.getName())
                .build();
    }

    @Bean
    public TopicExchange locationTopicExchange() {
        return new TopicExchange(RabbitMQConstant.LOCATION_EXCHANGE.getName());
    }

    @Bean
    public Binding locationBinding() {
        return BindingBuilder.bind(locationQueue())
                .to(locationTopicExchange())
                .with(RabbitMQConstant.LOCATION_ROUTING_KEY.getName());
    }

    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(RabbitMQConstant.NOTIFICATION_QUEUE.getName())
                .deadLetterExchange(RabbitMQConstant.DEAD_LETTER_EXCHANGE.getName())
                .deadLetterRoutingKey(RabbitMQConstant.DEAD_LETTER_ROUTING_KEY.getName())
                .build();
    }

    @Bean
    public DirectExchange notificationTopicExchange() {
        return new DirectExchange(RabbitMQConstant.NOTIFICATION_EXCHANGE.getName());
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(notificationTopicExchange())
                .with(RabbitMQConstant.NOTIFICATION_ROUTING_KEY.getName());
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(RabbitMQConstant.DEAD_LETTER_QUEUE.getName()).build();
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(RabbitMQConstant.DEAD_LETTER_EXCHANGE.getName());
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(RabbitMQConstant.DEAD_LETTER_ROUTING_KEY.getName());
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(rabbitMQProperties.getHost());
        factory.setPort(rabbitMQProperties.getPort());
        factory.setUsername(rabbitMQProperties.getUsername());
        factory.setPassword(rabbitMQProperties.getPassword());
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(final ConnectionFactory connectionFactory) {
        final RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);

        rabbitAdmin.declareQueue(locationQueue());
        rabbitAdmin.declareExchange(locationTopicExchange());
        rabbitAdmin.declareBinding(locationBinding());

        rabbitAdmin.declareQueue(notificationQueue());
        rabbitAdmin.declareExchange(notificationTopicExchange());
        rabbitAdmin.declareBinding(notificationBinding());

        rabbitAdmin.declareQueue(deadLetterQueue());
        rabbitAdmin.declareExchange(deadLetterExchange());
        rabbitAdmin.declareBinding(deadLetterBinding());

        return rabbitAdmin;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory) {
        final SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        factory.setRetryTemplate(retryTemplate());
        return factory;
    }

    @Bean
    public RetryTemplate retryTemplate() {
        final RetryTemplate retryTemplate = new RetryTemplate();

        final SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);

        final FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(3000);

        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        return retryTemplate;
    }
}
