package com.twtw.backend.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twtw.backend.global.constant.RabbitMQConstant;
import com.twtw.backend.global.properties.RabbitMQProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
        return new Queue(RabbitMQConstant.LOCATION_QUEUE.getName(), true);
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
        return new Queue(RabbitMQConstant.NOTIFICATION_QUEUE.getName(), true);
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
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
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
        return rabbitAdmin;
    }
}
