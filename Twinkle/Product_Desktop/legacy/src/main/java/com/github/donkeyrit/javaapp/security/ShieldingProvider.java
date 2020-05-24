package com.github.donkeyrit.javaapp.security;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class ShieldingProvider {

    public static String shielding(String input) {
        /**
         * Method escaped input string from special characters
         * which may protect from sql injection attasks
         */
        if (input == null || input.isEmpty()) {
            return "";
        }
        int len = input.length();
        final StringBuilder result = new StringBuilder(len + len / 4);
        final StringCharacterIterator iterator = new StringCharacterIterator(input);
        char ch = iterator.current();
        while (ch != CharacterIterator.DONE) {
            if (ch == '\n') {
                result.append("\\n");
            } else if (ch == '\r') {
                result.append("\\r");
            } else if (ch == '\'') {
                result.append("\\\'");
            } else if (ch == '"') {
                result.append("\\\"");
            } else {
                result.append(ch);
            }
            ch = iterator.next();
        }
        return result.toString();
    }
}
