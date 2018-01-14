package com.achievers.validator.rules;

import com.achievers.validator.contracts.BaseProperty;
import com.achievers.validator.contracts.BaseRule;

import java.util.Locale;

public class ValidIdRule implements BaseRule<String> {

    @Override
    public boolean validate(BaseProperty<String> property) {
        return property != null && property.getValue() != null && !property.getValue().equals("");
    }

    @Override
    public String getError(String name) {
        return String.format(Locale.getDefault(), "Please select %s.", name);
    }
}