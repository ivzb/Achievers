package com.achievers.validator;

import com.achievers.validator.contracts.BaseValidation;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Validation implements BaseValidation {

    private LinkedList<String> mErrors;

    public Validation() {
        mErrors = new LinkedList<>();
    }

    @Override
    public boolean isValid() {
        return mErrors.size() == 0;
    }

    @Override
    public List<String> getErrors() {
        return mErrors;
    }

    @Override
    public String getFirstError() {
        if (mErrors.size() == 0) return null;

        return mErrors.iterator().next();
    }

    @Override
    public void addError(String format, Object... args) {
        String error = String.format(Locale.getDefault(), format, args);
        mErrors.add(error);
    }
}