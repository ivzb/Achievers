package com.achievers.ui.achievements;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivityTest;
import com.achievers.ui._base._shadows.ResourcesCompatShadow;
import com.achievers.ui.achievement.AchievementActivity;
import com.achievers.ui.add_achievement.AddAchievementActivity;
import com.achievers.ui.home.HomeActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = { ResourcesCompatShadow.class })
public class AchievementsActivityTest extends AbstractActivityTest<HomeActivity> {

    public AchievementsActivityTest() {
        super(HomeActivity.class);
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