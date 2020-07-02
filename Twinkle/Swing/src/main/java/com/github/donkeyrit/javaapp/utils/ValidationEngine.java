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
    public boolean validate() {
        for (ValidationBinder binder : validationBinders) {
            if (!binder.validate()) {
                return false;
            }
        }

        return true;
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

        public boolean validate() {
            if (rule.get()) {
                return true;
            }

            action.accept(null);
            return false;
        }
    }
}
