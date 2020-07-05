package com.github.donkeyrit.javaapp.validation;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ValidationEngine {

    public ValidationEngine addRule(Predicate<Object> predicate, Consumer<Object> consumer) {
        return this;
    }

    public boolean validate() {
        return true;
    }

}
