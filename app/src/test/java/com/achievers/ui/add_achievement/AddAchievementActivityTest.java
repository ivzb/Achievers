package com.achievers.ui.add_achievement;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivityTest;
import com.achievers.ui._base._shadows.FileProviderShadow;
import com.achievers.ui._base._shadows.IntentShadow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.robolectric.Shadows.shadowOf;

@Config(shadows = { FileProviderShadow.class, IntentShadow.class })
@RunWith(RobolectricTestRunner.class)
public class AddAchievementActivityTest extends AbstractActivityTest<AddAchievementActivity> {

    public AddAchievementActivityTest() {
        super(AddAchievementActivity.class);
    }

    @Test
    public void fillFields_takePicture_save_finish() {
        fillTitle()
            .fillDescription()
            .clickInvolvement(3)
            .takePicture()
            .clickSave()
            .assertActivityFinishes();
    }

    @Test
    public void fillFields_choosePicture_save_finish() {
        fillTitle()
            .fillDescription()
            .clickInvolvement(2)
            .choosePicture()
            .clickSave()
            .assertActivityFinishes();
    }

    private AddAchievementActivityTest fillTitle() {
        EditText etTitle = mActivity.findViewById(R.id.etTitle);
        etTitle.setText("achievement");

        return this;
    }

    private AddAchievementActivityTest fillDescription() {
        EditText etDescription = mActivity.findViewById(R.id.etDescription);
        etDescription.setText("description");

        return this;
    }

    private AddAchievementActivityTest clickInvolvement(int position) {
        RecyclerView rvInvolvements = mActivity.findViewById(R.id.rvInvolvements);
        rvInvolvements.getChildAt(position).performClick();

        return this;
    }

    private AddAchievementActivityTest takePicture() {
        mActivity.findViewById(R.id.btnTakePicture).performClick();
        Intent intent = assertStarted(MediaStore.ACTION_IMAGE_CAPTURE);

        shadowOf(mActivity).receiveResult(
                intent,
                Activity.RESULT_OK,
                new Intent().setData(mock(Uri.class)));

        return this;
    }

    private AddAchievementActivityTest choosePicture() {
        mActivity.findViewById(R.id.btnChoosePicture).performClick();
        Intent intent = assertStarted(Intent.ACTION_PICK);
        receiveResult(intent);

        return this;
    }

    private AddAchievementActivityTest clickSave() {
        shadowOf(mActivity).clickMenuItem(R.id.menu_save);

        return this;
    }

    private void assertActivityFinishes() {
        assertTrue(shadowOf(mActivity).isFinishing());
    }

    private Intent assertStarted(String action) {
        Intent intent = shadowOf(mActivity).getNextStartedActivity();
        assertEquals(intent.getAction(), action);

        return intent;
    }

    private void receiveResult(Intent intent) {
        shadowOf(mActivity).receiveResult(
                intent,
                Activity.RESULT_OK,
                new Intent().setData(mock(Uri.class)));
    }
}