package com.github.donkeyrit.javaapp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ValidationEngine implements IValidationEngine {

    private List<ValidationBinder> validationBinders;

    public ValidationEngine() {
        validationBinders = new ArrayList<>();
    }

    @Override
    public void validate() {
        for (ValidationBinder binder : validationBinders) {
            binder.validate();
        }
    }

    @Override
    public IValidationEngine addRule(Supplier<Boolean> rule, Consumer<Object> action) {
        validationBinders.add(new ValidationBinder(rule, action));
        return this;
    }

    private class ValidationBinder {
        private final Supplier<Boolean> rule;
        private final Consumer<Object> action;

        public ValidationBinder(Supplier<Boolean> rule, Consumer<Object> action) {
            this.rule = rule;
            this.action = action;
        }

        public void validate() {
            if (rule.get()) {
                action.accept(null);
            }
        }
    }
}
