package com.achievers.utils;

import org.junit.Test;

public class PreconditionsTest {

    @Test(expected = NullPointerException.class)
    public void nullArgument_shouldThrow() {
        Preconditions.checkNotNull(null);
    }

    @Test
    public void stringArgument_shouldNotThrow() {
        Preconditions.checkNotNull("5");
    }
}