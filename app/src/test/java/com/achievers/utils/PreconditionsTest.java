package com.achievers.utils;

import org.junit.Test;

public class PreconditionsTest {

    @Test(expected = NullPointerException.class)
    public void nullReference_shouldThrow() {
        Preconditions.checkNotNull(null);
    }

    @Test
    public void stringReference_shouldNotThrow() {
        Preconditions.checkNotNull("5");
    }
}