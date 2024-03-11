package com.twtw.backend.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twtw.backend.global.constant.RabbitMQConstant;
import com.twtw.backend.global.properties.RabbitMQProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private final RabbitMQProperties rabbitMQProperties;
    private final ObjectMapper objectMapper;

    @Bean
    public Queue locationQueue() {
        return QueueBuilder.durable(RabbitMQConstant.LOCATION_QUEUE.getName())
                .withArgument(
                        "x-dead-letter-exchange", RabbitMQConstant.DEAD_LETTER_EXCHANGE.getName())
                .withArgument(
                        "x-dead-letter-routing-key",
                        RabbitMQConstant.DEAD_LETTER_ROUTING_KEY.getName())
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
                .withArgument(
                        "x-dead-letter-exchange", RabbitMQConstant.DEAD_LETTER_EXCHANGE.getName())
                .withArgument(
                        "x-dead-letter-routing-key",
                        RabbitMQConstant.DEAD_LETTER_ROUTING_KEY.getName())
                .build();
    }

    @Bean
    public TopicExchange notificationTopicExchange() {
        return new TopicExchange(RabbitMQConstant.NOTIFICATION_EXCHANGE.getName());
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
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(RabbitMQConstant.DEAD_LETTER_EXCHANGE.getName());
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
}
