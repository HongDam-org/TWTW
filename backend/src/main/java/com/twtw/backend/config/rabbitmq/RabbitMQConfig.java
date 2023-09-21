package com.twtw.backend.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twtw.backend.global.properties.RabbitMQProperties;

import lombok.RequiredArgsConstructor;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    private static final String QUEUE_NAME = "map.queue";
    private static final String EXCHANGE_NAME = "map.exchange";
    private static final String ROUTING_KEY = "group.*";
    private final RabbitMQProperties rabbitMQProperties;
    private final ObjectMapper objectMapper;

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(final Queue queue, final TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        rabbitTemplate.setRoutingKey(QUEUE_NAME);
        return rabbitTemplate;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(rabbitMQProperties.getHost());
        factory.setUsername(rabbitMQProperties.getUsername());
        factory.setPassword(rabbitMQProperties.getPassword());
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
