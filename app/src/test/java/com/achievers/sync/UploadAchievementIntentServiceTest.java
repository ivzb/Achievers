package com.achievers.sync;

import android.app.Notification;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;
import com.achievers.sync._base.AbstractIntentServiceTest;
import com.achievers.sync._base._shadows.IOUtilsShadow;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static com.achievers.sync.UploadAchievementIntentService.ACHIEVEMENT_EXTRA;
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
public class UploadAchievementIntentServiceTest
        extends AbstractIntentServiceTest<UploadAchievementIntentService> {

    public UploadAchievementIntentServiceTest() {
        super(UploadAchievementIntentService.class);
    }

    @Test
    public void noAchievement() {
        // arrange
        when(mIntent.getParcelableExtra(ACHIEVEMENT_EXTRA)).thenReturn(null);

        // act
        mIntentService.onHandleIntent(mIntent);

        // assert
        verify(mIntent).getParcelableExtra(ACHIEVEMENT_EXTRA);

        assertEquals(1, mShadowNotificationManager.size());

        Notification notification = mShadowNotificationManager.getNotification(ACHIEVEMENT_UPLOAD_NOTIFICATION_ID);
        assertNotNull(notification);
    }

    @Test
    public void pictureNotFound() {
        // arrange
        Parcelable achievement = Parcels.wrap(new Achievement()); // empty achievement
        when(mIntent.getParcelableExtra(ACHIEVEMENT_EXTRA)).thenReturn(achievement);

        // act
        mIntentService.onHandleIntent(mIntent);

        // assert
        verify(mIntent).getParcelableExtra(ACHIEVEMENT_EXTRA);

        assertEquals(1, mShadowNotificationManager.size());

        Notification notification = mShadowNotificationManager.getNotification(ACHIEVEMENT_UPLOAD_NOTIFICATION_ID);
        assertNotNull(notification);
    }

    @Test
    public void saveAchievement() {
        // arrange
        Parcelable achievement = Parcels.wrap(
                new Achievement(
                        "title",
                        "descrioption",
                        Involvement.Gold.getId(),
                        Uri.parse("fake-uri")));

        when(mIntent.getParcelableExtra(ACHIEVEMENT_EXTRA)).thenReturn(achievement);

        // act
        mIntentService.onHandleIntent(mIntent);

        // assert
        verify(mIntent).getParcelableExtra(ACHIEVEMENT_EXTRA);

        assertEquals(1, mShadowNotificationManager.size());

        Notification notification = mShadowNotificationManager.getNotification(ACHIEVEMENT_UPLOAD_NOTIFICATION_ID);
        assertNotNull(notification);
    }
}