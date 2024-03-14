package com.twtw.backend.support.repository;

import com.twtw.backend.config.database.QuerydslConfig;
import com.twtw.backend.support.database.ResetDatabase;
import com.twtw.backend.support.testcontainer.ContainerTestConfig;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@DataJpaTest
@Target(ElementType.TYPE)
@Import({ResetDatabase.class, QuerydslConfig.class})
@Retention(RetentionPolicy.RUNTIME)
@ContextConfiguration(initializers = {ContainerTestConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public @interface EnableDataJpa {}
