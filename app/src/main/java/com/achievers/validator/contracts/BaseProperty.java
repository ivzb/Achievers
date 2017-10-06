package com.achievers.validator.contracts;

public interface BaseProperty<T> {

    String getName();
    T getValue();

    void validate(BaseValidation validation);
}
