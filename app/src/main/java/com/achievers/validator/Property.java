package com.achievers.validator;

import com.achievers.validator.contracts.BaseProperty;
import com.achievers.validator.contracts.BaseRule;
import com.achievers.validator.contracts.BaseValidation;

import java.util.List;

public class Property<T> implements BaseProperty {

    private T mValue;
    private String mName;
    private List<BaseRule> mRules;

    public Property(String name, T value, List<BaseRule> rules) {
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
                validation.addError(rule.getError(mName));
            }
        }
    }
}