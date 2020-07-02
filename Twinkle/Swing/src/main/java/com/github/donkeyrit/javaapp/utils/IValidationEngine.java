package com.github.donkeyrit.javaapp.utils;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface IValidationEngine {

    IValidationEngine addRule(Supplier<Boolean> rule, Consumer<Object> action);
    void validate();
}
