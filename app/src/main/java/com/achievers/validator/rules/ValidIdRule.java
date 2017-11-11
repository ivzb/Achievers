package com.achievers.validator.rules;

import com.achievers.validator.contracts.BaseProperty;
import com.achievers.validator.contracts.BaseRule;

import java.util.Locale;

public class ValidIdRule implements BaseRule<Long> {

    @Override
    public boolean validate(BaseProperty<Long> property) {
        return property != null && property.getValue() != null && property.getValue() > 0;
    }

    @Override
    public String getError(String name) {
        return String.format(Locale.getDefault(), "Please select %s.", name);
    }
}