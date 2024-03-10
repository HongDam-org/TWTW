package com.twtw.backend.support.repository;

import com.twtw.backend.support.database.ResetDatabase;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@EnableDataJpa
@Import(ResetDatabase.class)
public abstract class RepositoryTest {

    @Autowired private ResetDatabase resetDatabase;

    @BeforeEach
    public void tearDown() {
        resetDatabase.reset();
    }
}
