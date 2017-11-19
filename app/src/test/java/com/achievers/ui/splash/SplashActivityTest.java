package com.achievers.ui.splash;

import android.content.Intent;

import com.achievers.ui._base.AbstractActivityTest;
import com.achievers.ui.home.HomeActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class SplashActivityTest extends AbstractActivityTest<SplashActivity> {

    public SplashActivityTest() {
        super(SplashActivity.class);
    }

    @Test
    public void shouldOpenAchievementsActivity() {
        Intent expectedIntent = new Intent(mActivity, HomeActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}