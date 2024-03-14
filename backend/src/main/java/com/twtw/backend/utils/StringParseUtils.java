package com.twtw.backend.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringParseUtils {

    private static final String BLANK = " ";
    private static final String PLUS = "+";

    public static String parse(final String input) {
        final StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length() - 1; i++) {
            if (i > 0) {
                result.append(BLANK);
            }
            result.append(PLUS).append(input, i, i + 2);
        }
        return result.toString();
    }
}
