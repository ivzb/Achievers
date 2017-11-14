package com.achievers.ui.evidence.views;

import android.app.Service;
import android.os.Build;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.ui.evidence.EvidenceFragmentTest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class)
public class EvidencePhotoFragmentTest extends EvidenceFragmentTest {

    @Mock Service mService;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment = new EvidencePhotoFragment();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

//        ShadowApplication.getInstance().setSystemService(Context.CAPTIONING_SERVICE, mService);
//        CaptioningManager

        startFragment(mFragment);

        verify(mPresenter).start();
        verify(mPresenter).requestReadExternalStoragePermission();
    }
}