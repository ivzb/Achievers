package com.achievers.validator;

import com.achievers.validator.contracts.BaseRule;
import com.achievers.validator.contracts.BaseValidation;
import com.achievers.validator.rules.NotNullRule;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class ValidatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void testBuilder_missingProperty() {
        new Validator().validate();
    }

    @Test(expected = NullPointerException.class)
    public void testBuilder_nullName() {
        new Validator()
                .addProperty(
                        null,
                        5,
                        new NotNullRule<>())
                .validate();
    }

    @Test(expected = NullPointerException.class)
    public void testBuilder_nullRule() {
        BaseRule rule = null;

        new Validator()
                .addProperty(
                        "test",
                        5,
                        rule)
                .validate();
    }

    @Test(expected = NullPointerException.class)
    public void testBuilder_nullRules() {
        List<BaseRule> rules = null;

        new Validator()
                .addProperty(
                        "test",
                        5,
                        rules)
                .validate();
    }

    @Test
    public void testBuilder_valid_oneRule() {
        BaseValidation validation = new Validator()
                .addProperty(
                        "test",
                        5,
                        new NotNullRule<>())
                .validate();

        assertTrue("validator expected to return true", validation.isValid());
    }

    @Test
    public void testBuilder_invalid_oneRule() {
        BaseValidation validation = new Validator()
                .addProperty(
                        "test",
                        null,
                        new NotNullRule<>())
                .validate();

        assertFalse("validator expected to return false", validation.isValid());
    }

    // todo: valid/invalid multiple rules
    // todo: different combinations
}