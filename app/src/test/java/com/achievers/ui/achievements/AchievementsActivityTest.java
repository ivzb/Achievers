package com.achievers.ui.achievements;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.R;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.add_achievement.AddAchievementActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP, constants = BuildConfig.class, application = AchieversDebugTestApplication.class)
public class AchievementsActivityTest {

    private Activity mActivity;

    @Before
    public void setUp() throws Exception {
        mActivity = Robolectric.setupActivity(Activity.class);
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

    @Test
    public void clickAchievement_shouldOpenAchievementActivity() {
        RecyclerView rvAchievements = mActivity.findViewById(R.id.rvAchievements);
        rvAchievements.getChildAt(0).performClick();

        Intent expectedIntent = new Intent(mActivity, AchievementActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }

    @Test
    public void clickAddAchievement_shouldOpenAddAchievementActivity() {
        FloatingActionButton fabAddAchievement = mActivity.findViewById(R.id.fabAddAchievement);
        fabAddAchievement.performClick();

        Intent expectedIntent = new Intent(mActivity, AddAchievementActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}