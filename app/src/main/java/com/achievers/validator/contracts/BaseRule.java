package com.achievers.validator.contracts;

public interface BaseRule<T> {

    boolean validate(BaseProperty<T> property);
    String getError(String name);
}
