package com.achievers.ui.voice_recording;

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

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP,
        constants = BuildConfig.class,
        application = AchieversDebugTestApplication.class,
        shadows = { ResourcesCompatShadow.class })
public class VoiceRecordingFragmentTest {

    private @Mock VoiceRecordingContract.Presenter mPresenter;
    private @Mock VoiceRecordingViewModel mViewModel;

    private @Mock File mFile;

    private VoiceRecordingFragment mFragment;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        mFragment = new VoiceRecordingFragment();
        mFragment.setPresenter(mPresenter);
        mFragment.setViewModel(mViewModel);

        startFragment(mFragment);

//        verify(mViewModel).addOnPropertyChangedCallback(isA(Observable.OnPropertyChangedCallback.class));

        verify(mPresenter).start();
        verify(mPresenter).requestRecordAudioPermission();
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
    public void getFile() {
        // act
        mFragment.getFile();

        // assert
        verify(mViewModel).getFile();
    }

    @Test
    public void setFile() {
        // act
        mFragment.setFile(mFile);

        // assert
        verify(mViewModel).setFile(eq(mFile));
    }

    @Test
    public void onRecord_start() {
        // act
        mFragment.onRecord(true);

        // assert
        verify(mPresenter).clickStartRecording();
    }

    @Test
    public void onRecord_stop() {
        // act
        mFragment.onRecord(false);

        // assert
        verify(mPresenter).clickStopRecording();
    }
}