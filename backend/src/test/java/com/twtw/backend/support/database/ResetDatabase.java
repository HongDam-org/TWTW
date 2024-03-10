package com.twtw.backend.support.database;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class ResetDatabase {

    @Autowired private Flyway flyway;

    public void reset() {
        flyway.clean();
        flyway.migrate();
    }
}
