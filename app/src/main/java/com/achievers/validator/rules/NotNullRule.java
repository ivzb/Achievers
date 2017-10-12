package com.achievers.validator.rules;

import com.achievers.validator.contracts.BaseProperty;
import com.achievers.validator.contracts.BaseRule;

import java.util.Locale;

public class NotNullRule<T> implements BaseRule<T> {

    @Override
    public boolean validate(BaseProperty<T> property) {
        return property != null && property.getValue() != null;
    }

    @Override
    public String getError(String name) {
        return String.format(Locale.getDefault(), "Please select %s.", name);
    }
}