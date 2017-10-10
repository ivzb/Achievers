package com.achievers.ui.add_achievement;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.achievers.R;
import com.achievers.ui.BaseActivityTest;

import org.junit.Test;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.robolectric.Shadows.shadowOf;

@Config(shadows = { FileProviderShadow.class})
public class AddAchievementActivityTest extends BaseActivityTest<AddAchievementActivity> {

    public AddAchievementActivityTest() {
        super(AddAchievementActivity.class);
    }

    @Test
    public void clickAchievement_shouldOpenAchievementActivity() {
        EditText etTitle = mActivity.findViewById(R.id.etTitle);
        etTitle.setText("achievement");

        EditText etDescription = mActivity.findViewById(R.id.etDescription);
        etDescription.setText("description");

        RecyclerView rvInvolvements = mActivity.findViewById(R.id.rvInvolvements);
        rvInvolvements.getChildAt(2).performClick();

        mActivity.findViewById(R.id.btnTakePicture).performClick();

        ShadowActivity shadowActivity = shadowOf(mActivity);

        Intent intent = shadowActivity.getNextStartedActivity();

        assertEquals(intent.getAction(), MediaStore.ACTION_IMAGE_CAPTURE);

        shadowActivity.receiveResult(
                intent,
                Activity.RESULT_OK,
                new Intent().setData(mock(Uri.class)));

        shadowActivity.clickMenuItem(R.id.menu_save);

        assertTrue(shadowActivity.isFinishing());
    }
}