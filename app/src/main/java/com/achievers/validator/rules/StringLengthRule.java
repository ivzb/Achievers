package com.achievers.validator.rules;

import com.achievers.validator.contracts.BaseProperty;

import java.util.Locale;

public class StringLengthRule extends NotNullRule<String> {

    private int mMinLength;
    private int mMaxLength;

    public StringLengthRule(int minLength) {
        this(minLength, Integer.MAX_VALUE);
    }

    public StringLengthRule(int minLength, int maxLength) {
        if (minLength < 0) {
            throw new IllegalArgumentException("maxLength should be greater than 0");
        }

        if (minLength >= maxLength) {
            throw new IllegalArgumentException("maxLength should be greater than minLength");
        }

        mMinLength = minLength;
        mMaxLength = maxLength;
    }

    @Override
    public boolean validate(BaseProperty<String> property) {
        return super.validate(property) &&
                property.getValue().length() >= mMinLength &&
                property.getValue().length() <= mMaxLength;
    }

    @Override
    public String getError(String name) {
        return String.format(Locale.getDefault(),
                "%s should be in range [%d - %d].",
                name,
                mMinLength,
                mMaxLength);
    }
}