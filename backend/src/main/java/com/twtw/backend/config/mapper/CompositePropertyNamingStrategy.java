package com.twtw.backend.config.mapper;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

public class CompositePropertyNamingStrategy extends PropertyNamingStrategy {
    private static final String CAMEL_CASE_PATTERN = "_";

    @Override
    public String nameForField(
            final MapperConfig<?> config, final AnnotatedField field, final String defaultName) {
        return propertyNamingStrategy(defaultName).nameForField(config, field, defaultName);
    }

    @Override
    public String nameForGetterMethod(
            final MapperConfig<?> config, final AnnotatedMethod method, final String defaultName) {
        return propertyNamingStrategy(defaultName).nameForGetterMethod(config, method, defaultName);
    }

    @Override
    public String nameForSetterMethod(
            final MapperConfig<?> config, final AnnotatedMethod method, final String defaultName) {
        return propertyNamingStrategy(defaultName).nameForSetterMethod(config, method, defaultName);
    }

    private PropertyNamingStrategy propertyNamingStrategy(final String defaultName) {
        if (defaultName.contains(CAMEL_CASE_PATTERN)) {
            return PropertyNamingStrategies.SNAKE_CASE;
        }
        return PropertyNamingStrategies.LOWER_CAMEL_CASE;
    }
}
