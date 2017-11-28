package com.achievers.utils;

import android.os.Build;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.google.android.exoplayer2.SimpleExoPlayer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(
        sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class)
public class ExoPlayerUtilsTest {

    @Test(expected = NullPointerException.class)
    public void nullContext() {
        ExoPlayerUtils.initialize(null);
    }

    @Test
    public void initialize() throws IllegalStateException {
        SimpleExoPlayer exoPlayer = ExoPlayerUtils.initialize(RuntimeEnvironment.application);
        assertNotNull(exoPlayer);
    }
}