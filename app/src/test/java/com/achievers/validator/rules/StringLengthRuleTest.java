package com.achievers.validator.rules;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class StringLengthRuleTest extends BaseRuleTest {

    @Test(expected = IllegalArgumentException.class)
    public void incorrectMinLength() {
        validate("test", new StringLengthRule(-1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void incorrectMinLength_correctMaxLength() {
        validate("test", new StringLengthRule(-1, 5));
    }

    @Test(expected = IllegalArgumentException.class)
    public void correctMinLength_incorrectMaxLength() {
        validate("test", new StringLengthRule(3, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void sameLength() {
        assertFalse(validate("test", new StringLengthRule(4, 4)));
    }

    @Test
    public void nullValue_correctMinLength() {
        assertFalse(validate(null, new StringLengthRule(5)));
    }

    @Test
    public void nullValue_correctMinLength_correctMaxLength() {
        assertFalse(validate(null, new StringLengthRule(2, 3)));
    }

    @Test
    public void failingMinLength() {
        assertFalse(validate("test", new StringLengthRule(5)));
    }

    @Test
    public void failingMinLength_correctMaxLength() {
        assertFalse(validate("test", new StringLengthRule(5, 7)));
    }

    @Test
    public void correctMinLength_failingMaxLength() {
        assertFalse(validate("testtest", new StringLengthRule(5, 7)));
    }

    @Test
    public void correctMinLength() {
        assertTrue(validate("test", new StringLengthRule(2)));
    }

    @Test
    public void correctMinLength_correctMaxLength() {
        assertTrue(validate("test", new StringLengthRule(2, 4)));
    }

    @Test
    public void emptyString_correctMinLength() {
        assertTrue(validate("", new StringLengthRule(0)));
    }

    @Test
    public void emptyString_correctMinLength_correctMaxLength() {
        assertTrue(validate("", new StringLengthRule(0, 1)));
    }
}