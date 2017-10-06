package com.achievers.validator.contracts;

import java.util.List;

public interface BaseValidation {

    boolean isValid();

    List<String> getErrors();
    String getFirstError();

    void addError(String format, Object... args);
}
