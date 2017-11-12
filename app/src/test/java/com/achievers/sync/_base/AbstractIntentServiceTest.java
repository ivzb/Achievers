package com.achievers.sync._base;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowNotificationManager;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class)
public abstract class AbstractIntentServiceTest<IntentService extends Service> {

    protected  @Mock Intent mIntent;

    protected IntentService mIntentService;
    private ServiceController<IntentService> mController;

    protected ShadowNotificationManager mShadowNotificationManager;

    private final Class<IntentService> mIntentServiceClass;

    public AbstractIntentServiceTest(Class<IntentService> intentServiceClass) {
        mIntentServiceClass = intentServiceClass;
    }

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mController = Robolectric.buildService(mIntentServiceClass);
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
}
