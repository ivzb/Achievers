package com.achievers.ui;

import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import com.achievers.R;
import com.achievers.ui._base.AbstractScreenTest;
import com.achievers.ui.achievements.AchievementsActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AchievementsScreenTest extends AbstractScreenTest<AchievementsActivity> {

    public AchievementsScreenTest() {
        super(AchievementsActivity.class);
    }

    @Test
    public void clickAddAchievementButton_opensAddAchievementUi() {
//        onView(withId(R.id.fabAddAchievement)).perform(click());
        onView(withId(R.id.clAddAchievement)).check(matches(isDisplayed()));
    }

    @Test
    public void clickAchievement_opensAchievementUi() {
        onView(withId(R.id.rvAchievements))
                .perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.rvEvidences)).check(matches(isDisplayed()));
    }
}