package com.twtw.backend.config.mapper;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class CompositePropertyNamingStrategy extends PropertyNamingStrategy {
    private final PropertyNamingStrategy[] strategies;

    public CompositePropertyNamingStrategy(final PropertyNamingStrategy... strategies) {
        this.strategies = strategies;
    }

    @Override
    public String nameForField(final MapperConfig<?> config, final AnnotatedField field, final String defaultName) {
        String name = defaultName;
        for (final PropertyNamingStrategy strategy : strategies) {
            name = strategy.nameForField(config, field, name);
        }
        return name;
    }

    @Override
    public String nameForGetterMethod(final MapperConfig<?> config, final AnnotatedMethod method, final String defaultName) {
        String name = defaultName;
        for (final PropertyNamingStrategy strategy : strategies) {
            name = strategy.nameForGetterMethod(config, method, name);
        }
        return name;
    }

    @Override
    public String nameForSetterMethod(final MapperConfig<?> config, final AnnotatedMethod method, final String defaultName) {
        String name = defaultName;
        for (final PropertyNamingStrategy strategy : strategies) {
            name = strategy.nameForSetterMethod(config, method, name);
        }
        return name;
    }
}
