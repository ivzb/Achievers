package com.achievers.sync;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.data.entities.Achievement;
import com.achievers.data.entities.Involvement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.parceler.Parcels;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNotificationManager;

import static com.achievers.sync.UploadAchievementIntentService.ACHIEVEMENT_EXTRA;
import static com.achievers.utils.NotificationUtils.ACHIEVEMENT_UPLOAD_NOTIFICATION_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class)
public class UploadAchievementIntentServiceTest {

    private @Mock Intent mIntent;

    private UploadAchievementIntentService mIntentService;
    private ServiceController<UploadAchievementIntentService> mController;

    private ShadowNotificationManager mShadowNotificationManager;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mController = Robolectric.buildService(UploadAchievementIntentService.class);
        mIntentService = mController.create().get();

        mShadowNotificationManager = shadowOf((NotificationManager)
                RuntimeEnvironment.application.getSystemService(Context.NOTIFICATION_SERVICE));
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mIntent);
        mController.destroy();
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(mIntentService);
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
                        Involvement.Gold,
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