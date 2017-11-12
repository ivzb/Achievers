package com.achievers.sync;

import android.app.Notification;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Evidence;
import com.achievers.sync._base.AbstractIntentServiceTest;
import com.achievers.sync._base._shadows.IOUtilsShadow;
import com.achievers.utils.ui.multimedia.MultimediaType;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.achievers.sync.UploadEvidenceIntentService.EVIDENCE_EXTRA;
import static com.achievers.utils.NotificationUtils.ACHIEVEMENT_UPLOAD_NOTIFICATION_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        shadows = { IOUtilsShadow.class },
        application = AchieversDebugTestApplication.class)
public class UploadEvidenceIntentServiceTest
        extends AbstractIntentServiceTest<UploadEvidenceIntentService> {

    public UploadEvidenceIntentServiceTest() {
        super(UploadEvidenceIntentService.class);
    }

    @Test
    public void noEvidence() {
        // arrange
        when(mIntent.getParcelableExtra(EVIDENCE_EXTRA)).thenReturn(null);

        // act
        mIntentService.onHandleIntent(mIntent);

        // assert
        verify(mIntent).getParcelableExtra(EVIDENCE_EXTRA);

        assertEquals(1, mShadowNotificationManager.size());

        Notification notification = mShadowNotificationManager.getNotification(ACHIEVEMENT_UPLOAD_NOTIFICATION_ID);
        assertNotNull(notification);
    }

    @Test
    public void fileNotFound() {
        // arrange
        Parcelable evidence = Parcels.wrap(new Evidence()); // empty achievement
        when(mIntent.getParcelableExtra(EVIDENCE_EXTRA)).thenReturn(evidence);

        // act
        mIntentService.onHandleIntent(mIntent);

        // assert
        verify(mIntent).getParcelableExtra(EVIDENCE_EXTRA);

        assertEquals(1, mShadowNotificationManager.size());

        Notification notification = mShadowNotificationManager.getNotification(ACHIEVEMENT_UPLOAD_NOTIFICATION_ID);
        assertNotNull(notification);
    }

    @Test
    public void saveEvidence() {
        // arrange
        Parcelable evidence = Parcels.wrap(
                new Evidence(
                        "title",
                        1,
                        MultimediaType.Video,
                        Uri.parse("fake-uri")));

        when(mIntent.getParcelableExtra(EVIDENCE_EXTRA)).thenReturn(evidence);

        // act
        mIntentService.onHandleIntent(mIntent);

        // assert
        verify(mIntent).getParcelableExtra(EVIDENCE_EXTRA);

        assertEquals(1, mShadowNotificationManager.size());

        Notification notification = mShadowNotificationManager.getNotification(ACHIEVEMENT_UPLOAD_NOTIFICATION_ID);
        assertNotNull(notification);
    }
}