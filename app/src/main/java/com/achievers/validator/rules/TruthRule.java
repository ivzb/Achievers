package com.achievers.validator.rules;

import com.achievers.validator.contracts.BaseProperty;

import java.util.Locale;

public class TruthRule extends NotNullRule<Boolean> {

    @Override
    public boolean validate(BaseProperty<Boolean> property) {
        return super.validate(property) && property.getValue();
    }

    @Override
    public String getError(String name) {
        return String.format(Locale.getDefault(), "%s should be true.", name);
    }
}