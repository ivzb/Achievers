package com.achievers.ui.quests;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivityTest;
import com.achievers.ui._base._shadows.ResourcesCompatShadow;
import com.achievers.ui.home.HomeActivity;
import com.achievers.ui.quest.QuestActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = { ResourcesCompatShadow.class })
public class QuestsActivityTest extends AbstractActivityTest<HomeActivity> {

    public QuestsActivityTest() {
        super(HomeActivity.class);
    }

    @Test
    public void clickQuest_shouldOpenQuestActivity() {
        RecyclerView rvQuests = mActivity.findViewById(R.id.rvQuests);
        rvQuests.getChildAt(0).performClick();

        Intent expectedIntent = new Intent(mActivity, QuestActivity.class);
        Intent actual = ShadowApplication.getInstance().getNextStartedActivity();
        assertEquals(expectedIntent.getComponent(), actual.getComponent());
    }
}