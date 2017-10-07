package com.achievers.validator;

import android.content.Context;

import com.achievers.R;
import com.achievers.validator.contracts.BaseRule;
import com.achievers.validator.contracts.BaseValidation;
import com.achievers.validator.rules.NotNullRule;
import com.achievers.validator.rules.StringLengthRule;
import com.achievers.validator.rules.TruthRule;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ValidatorTest {

    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();


    @Test(expected = IllegalArgumentException.class)
    public void testBuilder_missingProperty() {
        new Validator().validate();
    }

    @Test(expected = NullPointerException.class)
    public void testBuilder_nullContext() {
        new Validator(null);
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
    public void testBuilder_contextName() {
        Context context = mock(Context.class);

        String mockedMessage = "mocked string";

        when(context.getString(any(int.class))).thenReturn(mockedMessage);

        BaseRule<Integer> rule = new NotNullRule<>();

        BaseValidation validation = new Validator(context)
                .addProperty(
                        R.string.title,
                        null,
                        rule)
                .validate();

        assertFalse("validator expected to return false", validation.isValid());

        boolean hasOnlyOneError = validation.getErrors().size() == 1;
        assertTrue("only one error expected", hasOnlyOneError);

        String expected = rule.getError(mockedMessage);
        String actual = validation.getFirstError();
        assertEquals("mocked message mismatch", expected, actual);
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

    @Test
    public void testBuilder_valid_twoRule() {
        BaseValidation validation = new Validator()
                .addProperty(
                        "test",
                        "value",
                        new StringLengthRule(3, 5))
                .addProperty(
                        "another test",
                        true,
                        new TruthRule())
                .validate();

        assertTrue("validator expected to return true", validation.isValid());
    }

    @Test
    public void testBuilder_twoRules_firstInvalid() {
        String propertyName = "test";
        BaseRule<String> rule = new StringLengthRule(3, 4);

        BaseValidation validation = new Validator()
                .addProperty(
                        propertyName,
                        "value",
                        rule)
                .addProperty(
                        "another test",
                        true,
                        new TruthRule())
                .validate();

        assertFalse("validator expected to return false", validation.isValid());

        String expected = rule.getError(propertyName);
        String actual = validation.getFirstError();
        assertEquals("StringLengthRule error expected", expected, actual);

        boolean hasOnlyOneError = validation.getErrors().size() == 1;
        assertTrue("only one error expected", hasOnlyOneError);
    }

    @Test
    public void testBuilder_twoRules_secondInvalid() {
        String propertyName = "another test";
        BaseRule<Boolean> rule = new TruthRule();

        BaseValidation validation = new Validator()
                .addProperty(
                        "test",
                        "value",
                        new StringLengthRule(3, 5))
                .addProperty(
                        propertyName,
                        false,
                        rule)
                .validate();

        assertFalse("validator expected to return false", validation.isValid());

        String expected = rule.getError(propertyName);
        String actual = validation.getFirstError();
        assertEquals("TruthRule error expected", expected, actual);

        boolean hasOnlyOneError = validation.getErrors().size() == 1;
        assertTrue("only one error expected", hasOnlyOneError);
    }

    @Test
    public void testBuilder_twoRules_bothInvalid() {
        String firstPropertyName = "test";
        BaseRule<String> firstRule = new StringLengthRule(3, 4);

        String secondPropertyName = "another test";
        BaseRule<Boolean> secondRule = new TruthRule();

        BaseValidation validation = new Validator()
                .addProperty(
                        firstPropertyName,
                        "value",
                        firstRule)
                .addProperty(
                        secondPropertyName,
                        false,
                        secondRule)
                .validate();

        assertFalse("validator expected to return false", validation.isValid());

        boolean hasTwoErrors = validation.getErrors().size() == 2;
        assertTrue("two errors expected", hasTwoErrors);

        String firstExpected = firstRule.getError(firstPropertyName);
        String firstActual = validation.getFirstError();
        assertEquals("StringLengthRule error expected", firstExpected, firstActual);

        firstActual = validation.getErrors().get(0);
        assertEquals("StringLengthRule error expected", firstExpected, firstActual);

        String secondExpected = secondRule.getError(secondPropertyName);
        String secondActual = validation.getErrors().get(1);
        assertEquals("TruthRule error expected", secondExpected, secondActual);
    }
}