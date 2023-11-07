package com.twtw.backend.support.database;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class DatabaseTestConfig {
    @Bean
    public JPAQueryFactory jpaQueryFactory(final EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
