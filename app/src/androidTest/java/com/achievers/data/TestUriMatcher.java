package com.achievers.data;

import android.support.test.runner.AndroidJUnit4;

import com.achievers.provider.AchieversProviderUriMatcher;
import com.achievers.provider.AchieversUriEnum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestUriMatcher {

    private AchieversProviderUriMatcher mUriMatcher;

    @Before
    public void before() {
        this.mUriMatcher = new AchieversProviderUriMatcher();
    }

    @Test
    public void testUriMatcher() {
        String uriCodeDoesNotMatch = "Error: The uri code was matched incorrectly.";

        for (AchieversUriEnum expectedUriEnum: AchieversUriEnum.values()) {
            AchieversUriEnum actualUriEnum = mUriMatcher.matchCode(expectedUriEnum.code);

            assertEquals(uriCodeDoesNotMatch,
                    expectedUriEnum,
                    actualUriEnum);
        }
    }
}