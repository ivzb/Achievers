package com.achievers.validator.rules;

import com.achievers.validator.Property;
import com.achievers.validator.Validation;
import com.achievers.validator.contracts.BaseProperty;
import com.achievers.validator.contracts.BaseRule;

import java.util.Arrays;

abstract class BaseRuleTest {

    protected boolean validate(Object value, BaseRule rule) {
        BaseProperty property = new Property<>("test", value, Arrays.asList(rule));
        Validation validation = new Validation();
        property.validate(validation);

        return validation.isValid();
    }
}
