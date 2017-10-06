package com.achievers.validator.rules;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class NotNullRuleTest extends BaseRuleTest {

    @Test
    public void testNotNullRule_withValue() {
        assertTrue(validate(5, new NotNullRule<>()));
    }

    @Test
    public void testNotNullRule_withNull() {
        assertFalse(validate(null, new NotNullRule<>()));
    }
}
