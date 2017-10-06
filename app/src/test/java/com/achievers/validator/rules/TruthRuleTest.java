package com.achievers.validator.rules;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TruthRuleTest extends BaseRuleTest {

    @Test
    public void withNull() {
        assertFalse(validate(null, new TruthRule()));
    }

    @Test(expected = ClassCastException.class)
    public void withInt() {
        assertFalse(validate(5, new TruthRule()));
    }

    @Test
    public void withFalse() {
        assertFalse(validate(false, new TruthRule()));
    }

    @Test
    public void wthTrue() {
        assertTrue(validate(true, new TruthRule()));
    }
}
