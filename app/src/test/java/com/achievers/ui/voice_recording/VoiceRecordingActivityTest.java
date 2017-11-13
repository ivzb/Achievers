package com.achievers.ui.voice_recording;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivityTest;
import com.achievers.ui._base._shadows.ResourcesCompatShadow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(shadows = { ResourcesCompatShadow.class })
public class VoiceRecordingActivityTest extends AbstractActivityTest<VoiceRecordingActivity> {

    public VoiceRecordingActivityTest() {
        super(VoiceRecordingActivity.class);
    }

    @Test
    public void fillFields_takePicture_save_finish() {
        mActivity.findViewById(R.id.rvbRecordButton).performClick();
        mActivity.findViewById(R.id.rvbRecordButton).performClick();

        assertTrue(shadowOf(mActivity).isFinishing());
    }
}