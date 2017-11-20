package com.achievers.ui.evidence;

import android.os.Build;

import com.achievers.AchieversDebugTestApplication;
import com.achievers.BuildConfig;
import com.achievers.ui._base._shadows.ResourcesCompatShadow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.robolectric.Shadows.shadowOf;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class,
        shadows = { ResourcesCompatShadow.class })
public abstract class EvidenceFragmentTest {

    protected @Mock EvidenceContract.Presenter mPresenter;
    protected @Mock EvidenceViewModel mViewModel;

    protected EvidenceView mFragment;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        startFragment(mFragment);

        verify(mPresenter).start();
        verify(mPresenter).requestReadExternalStoragePermission();
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mViewModel);
        verifyNoMoreInteractions(mPresenter);
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(mFragment);
    }

    @Test
    public void onRequestPermissionsResult() {
        // arrange
        int requestCode = 5;
        String[] permissions = new String[] {};
        int[] grantResults = new int[] { 3 };

        // act
        mFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // assert
        verify(mPresenter).deliverPermissionsResult(
                eq(requestCode),
                eq(grantResults));
    }

    @Test
    public void showMultimediaError() {
        // act
        mFragment.showMultimediaError();

        // assert
        verify(mViewModel).setMultimediaFailed();
    }

    @Test
    public void finish() {
        // act
        mFragment.finish();

        // assert
        assertTrue(shadowOf(mFragment.getActivity()).isFinishing());
    }
}