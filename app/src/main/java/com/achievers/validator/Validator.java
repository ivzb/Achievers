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
}