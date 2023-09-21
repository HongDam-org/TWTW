package com.twtw.backend.config.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ObjectMapperConfig {

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
                .setPropertyNamingStrategy(
                        new CompositePropertyNamingStrategy(
                                PropertyNamingStrategies.SNAKE_CASE,
                                PropertyNamingStrategies.LOWER_CAMEL_CASE));
    }
}
