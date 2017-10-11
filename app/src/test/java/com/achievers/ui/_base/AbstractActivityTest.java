package com.achievers.ui._base;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertNotNull;

@Config(sdk = Build.VERSION_CODES.LOLLIPOP, constants = BuildConfig.class, application = AchieversDebugTestApplication.class)
public abstract class AbstractActivityTest<A extends AppCompatActivity> {

    protected A mActivity;

    private final Class<A> mActivityClass;

    public AbstractActivityTest(Class<A> activityClass) {
        mActivityClass = activityClass;
    }

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.setupActivity(mActivityClass);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(mActivity);
    }

    @Test
    public void shouldHaveFragment() throws Exception {
        android.support.v4.app.Fragment fragment = mActivity
                .getSupportFragmentManager()
                .findFragmentById(R.id.contentFrame);

        assertNotNull(fragment);
    }
}