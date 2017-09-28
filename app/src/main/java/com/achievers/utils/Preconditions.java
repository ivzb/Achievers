package com.achievers.utils;

public class Preconditions {

    public static <T> void checkNotNull(T obj) {
        if (null == obj) {
            throw new NullPointerException("Argument is null.");
        }
    }
}
