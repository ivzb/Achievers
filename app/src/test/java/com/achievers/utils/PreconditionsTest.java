package com.achievers.utils;

import org.junit.Test;

public class PreconditionsTest {

    @Test(expected = NullPointerException.class)
    public void nullReference_shouldThrow_withDefaultMessage() {
        Preconditions.checkNotNull(null);
    }

    @Test(expected = NullPointerException.class)
    public void nullReference_shouldThrow_withCustomMessage() {
        Preconditions.checkNotNull(null, "custom message");
    }

    @Test
    public void stringReference_shouldNotThrow() {
        Preconditions.checkNotNull("5");
    }
}