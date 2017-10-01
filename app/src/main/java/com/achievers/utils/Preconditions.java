package com.achievers.utils;

import javax.annotation.Nullable;

public class Preconditions {

    public static <T> void checkNotNull(T reference) {
        checkNotNull(reference, "Reference is null.");
    }

    public static <T> void checkNotNull(T reference, @Nullable String errorMessage) {
        if (null == reference) {
            throw new NullPointerException(errorMessage);
        }
    }
}