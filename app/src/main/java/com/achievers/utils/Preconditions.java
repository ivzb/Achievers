package com.achievers.utils;

import javax.annotation.Nullable;

public class Preconditions {

    public static <T> void checkNotNull(T reference) {

    }

    public static <T> void checkNotNull(T reference, @Nullable String errorMessage) {
        if (null == reference) {
            throw new NullPointerException(errorMessage);
        }
    }

    private static <T> void check(T reference, String errorMessage) {
        if (null == reference) {
            throw new NullPointerException("Reference is null.");
        }
    }
}