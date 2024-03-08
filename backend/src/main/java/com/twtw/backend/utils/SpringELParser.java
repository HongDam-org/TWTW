package com.twtw.backend.utils;

import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringELParser {

    private static final ExpressionParser PARSER = new SpelExpressionParser();
    private static final Integer START_INDEX = 0;

    public Object getDynamicValue(final String[] parameterNames, final Object[] args, final String key) {
        final StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = START_INDEX; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }

        return PARSER.parseExpression(key).getValue(context, Object.class);
    }
}
