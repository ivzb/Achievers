package com.achievers.validator;

import android.content.Context;
import android.support.annotation.NonNull;

import com.achievers.validator.contracts.BaseProperty;
import com.achievers.validator.contracts.BaseRule;
import com.achievers.validator.contracts.BaseValidation;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.achievers.utils.Preconditions.checkNotNull;

public class Validator {

    private final LinkedList<BaseProperty> mProperties;
    private final Context mContext;

    public Validator() {
        this(null);
    }

    public Validator(Context context) {
        checkNotNull(context);

        mProperties = new LinkedList<>();
        mContext = context;
    }

    public Validator addProperty(
            int resId,
            Object value,
            @NonNull final BaseRule rule) {

        checkNotNull(mContext);
        checkNotNull(rule);

        String name = mContext.getString(resId);
        return addProperty(name, value, rule);
    }

    public Validator addProperty(
            @NonNull String name,
            Object value,
            @NonNull final BaseRule rule) {

        checkNotNull(rule);

        return addProperty(name, value, Arrays.asList(rule));
    }

    public Validator addProperty(
            int resId,
            Object value,
            @NonNull List<BaseRule> rules) {

        checkNotNull(mContext);
        checkNotNull(rules);

        String name = mContext.getString(resId);
        return addProperty(name, value, rules);
    }

    public Validator addProperty(
            @NonNull String name,
            Object value,
            @NonNull List<BaseRule> rules) {

        checkNotNull(name);
        checkNotNull(rules);

        BaseProperty property = new Property<>(name, value, rules);
        mProperties.add(property);

        return this;
    }

    public BaseValidation validate() {
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