package com.achievers.ui.voice_recording;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;

import com.achievers.utils.files.factory.FileFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.support.v4.content.PermissionChecker.PERMISSION_DENIED;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static com.achievers.ui.voice_recording.VoiceRecordingPresenter.REQUEST_RECORD_AUDIO_PERMISSION;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class VoiceRecordingPresenterTest {

    @Mock private Context mContext;
    @Mock private VoiceRecordingContract.View mView;
    @Mock private FileFactory mFileFactory;
    @Mock private MediaRecorder mMediaRecorder;

    @Mock private File mFile;

    @Captor private ArgumentCaptor<String[]> mPermissionsCaptor;

    private VoiceRecordingContract.Presenter mPresenter;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new VoiceRecordingPresenter(
                mContext,
                mView,
                mFileFactory,
                mMediaRecorder);
    }

    @After
    public void after() {
        verifyNoMoreInteractions(mView);
        verifyNoMoreInteractions(mMediaRecorder);
    }

    @Test
    public void start_createFile() throws IOException {
        // arrange
        when(mFileFactory.createFile()).thenReturn(mFile);

        // act
        mPresenter.start();

        // assert
        verify(mFileFactory).createFile();
        verify(mView).setFile(mFile);
    }

    @Test
    public void start_throw() throws IOException {
        // arrange
        when(mFileFactory.createFile()).thenThrow(IOException.class);

        // act
        mPresenter.start();

        // assert
        verify(mFileFactory).createFile();
        verify(mView).finish(eq(RESULT_CANCELED), isNull(Intent.class));
    }

    @Test
    public void requestRecordAudioPermission() {
        // arrange
        String[] expectedPermissions = { Manifest.permission.RECORD_AUDIO };

        // act
        mPresenter.requestRecordAudioPermission();

        // assert
        verify(mView).requestPermissions(mPermissionsCaptor.capture(), eq(REQUEST_RECORD_AUDIO_PERMISSION));

        String[] actualPermissions = mPermissionsCaptor.getValue();

        assertTrue(actualPermissions.length == 1);
        assertEquals(expectedPermissions[0], actualPermissions[0]);
    }

    @Test
    public void clickStartRecording_throw() throws IOException {
        // arrange
        when(mView.getFile()).thenThrow(IOException.class);

        // act
        mPresenter.clickStartRecording();

        // assert
        verify(mView).getFile();
        verify(mView).finish(eq(RESULT_CANCELED), isNull(Intent.class));
    }

    @Test
    public void clickStartRecording_start() throws IOException {
        // arrange
        when(mFile.getAbsolutePath()).thenReturn("path");
        when(mView.getFile()).thenReturn(mFile);

        // act
        mPresenter.clickStartRecording();

        // assert
        verify(mMediaRecorder).setAudioSource(eq(MediaRecorder.AudioSource.MIC));
        verify(mMediaRecorder).setOutputFormat(eq(MediaRecorder.OutputFormat.THREE_GPP));
        verify(mView).getFile();
        verify(mMediaRecorder).setOutputFile(anyString());
        verify(mMediaRecorder).setAudioEncoder(eq(MediaRecorder.AudioEncoder.AMR_NB));

        verify(mMediaRecorder).prepare();
        verify(mMediaRecorder).start();
    }

    @Test
    public void clickStopRecording() {
        // act
        mPresenter.clickStopRecording();

        // assert
        verify(mMediaRecorder).stop();
        verify(mMediaRecorder).release();

        verify(mView).getFile();
        verify(mView).finish(eq(RESULT_OK), isA(Intent.class));
    }

    @Test
    public void deliverPermissionsResult_recordAudio_permissionGranted() {
        // arrange
        int requestCode = REQUEST_RECORD_AUDIO_PERMISSION;
        int[] grantResults = new int[] { PERMISSION_GRANTED };

        // act
        mPresenter.deliverPermissionsResult(requestCode, grantResults);
    }

    @Test
    public void deliverPermissionsResult_recordAudio_permissionDenied() {
        // arrange
        int requestCode = REQUEST_RECORD_AUDIO_PERMISSION;
        int[] grantResults = new int[] { PERMISSION_DENIED };

        // act
        mPresenter.deliverPermissionsResult(requestCode, grantResults);

        // assert
        verify(mView).finish(eq(RESULT_CANCELED), isNull(Intent.class));
    }

    @Test
    public void deliverPermissionsResult_notRecordAudio_permissionGranted() {
        // arrange
        int requestCode = -1;
        int[] grantResults = new int[] { PERMISSION_GRANTED };

        // act
        mPresenter.deliverPermissionsResult(requestCode, grantResults);

        // assert
        verify(mView).finish(eq(RESULT_CANCELED), isNull(Intent.class));
    }
}