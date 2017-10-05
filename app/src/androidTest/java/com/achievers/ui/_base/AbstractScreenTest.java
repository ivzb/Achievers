package com.achievers.ui._base;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

public abstract class AbstractScreenTest<T extends AbstractActivity> {

    @Rule
    public ActivityTestRule<T> mActivityTestRule;

    public AbstractScreenTest(Class<T> type) {
        mActivityTestRule = new ActivityTestRule<>(type);
    }
}