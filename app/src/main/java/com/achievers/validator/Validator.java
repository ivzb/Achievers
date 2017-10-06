package com.achievers.validator;

import android.support.annotation.NonNull;

import com.achievers.validator.contracts.BaseProperty;
import com.achievers.validator.contracts.BaseRule;
import com.achievers.validator.contracts.BaseValidation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static com.achievers.utils.Preconditions.checkNotNull;

class Validator {

    private final LinkedList<BaseProperty> mProperties;

    Validator() {
        mProperties = new LinkedList<>();
    }

    Validator addProperty(
            @NonNull String name,
            Object value,
            @NonNull final BaseRule rule) {

        checkNotNull(rule);

        return addProperty(name, value, Arrays.asList(rule));
    }

    Validator addProperty(
            @NonNull String name,
            Object value,
            @NonNull List<BaseRule> rules) {

        checkNotNull(name);
        checkNotNull(rules);

        BaseProperty property = new Property<>(name, value, rules);
        mProperties.add(property);

        return this;
    }

    BaseValidation validate() {
        if (mProperties.isEmpty()) {
            throw new IllegalArgumentException("No properties for validation");
        }

        BaseValidation validation = new Validation();

        for (BaseProperty property: mProperties) {
            property.validate(validation);
        }

        return validation;
    }

    private class Validation implements BaseValidation {

        private LinkedList<String> mErrors;

        Validation() {
            mErrors = new LinkedList<>();
        }

        @Override
        public boolean isValid() {
            return mErrors.size() == 0;
        }

        @Override
        public List<String> getErrors() {
            return mErrors;
        }

        @Override
        public String getFirstError() {
            if (mErrors.size() == 0) return null;

            return mErrors.iterator().next();
        }

        @Override
        public void addError(String format, Object... args) {
            String error = String.format(Locale.getDefault(), format, args);
            mErrors.add(error);
        }
    }

    private class Property<T> implements BaseProperty {

        private T mValue;
        private String mName;
        private List<BaseRule> mRules;

        Property(String name, T value, List<BaseRule> rules) {
            mName = name;
            mValue = value;
            mRules = rules;
        }

        @Override
        public String getName() {
            return this.mName;
        }

        @Override
        public T getValue() {
            return this.mValue;
        }

        @Override
        public void validate(BaseValidation validation) {
            for (BaseRule rule: mRules) {
                if (!rule.validate(this)) {
                    validation.addError(getName());
                }
            }
        }
    }
}