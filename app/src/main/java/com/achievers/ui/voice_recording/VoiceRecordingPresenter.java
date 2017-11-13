package com.achievers.ui.voice_recording;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.achievers.ui._base.AbstractPresenter;
import com.achievers.utils.files.factory.FileFactory;

import java.io.File;
import java.io.IOException;

import static com.achievers.utils.Preconditions.checkNotNull;

public class VoiceRecordingPresenter
        extends AbstractPresenter<VoiceRecordingContract.View>
        implements VoiceRecordingContract.Presenter {

    @VisibleForTesting
    static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private FileFactory mFileFactory;
    private MediaRecorder mRecorder;

    VoiceRecordingPresenter(
            @NonNull Context context,
            @NonNull VoiceRecordingContract.View view,
            @NonNull FileFactory fileFactory,
            @NonNull MediaRecorder recorder) {

        mContext = checkNotNull(context);
        mView = checkNotNull(view);
        mFileFactory = checkNotNull(fileFactory);
        mRecorder = checkNotNull(recorder);
    }

    @Override
    public void start() {
        try {
            File file = mFileFactory.createFile();
            mView.setFile(file);
        } catch (IOException e) {
            finishCanceled();
        }
    }

    @Override
    public void requestRecordAudioPermission() {
        String[] permissions = { Manifest.permission.RECORD_AUDIO };
        mView.requestPermissions(permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    @Override
    public void clickStartRecording() {
        try {
            String outputPath = mView.getFile().getAbsolutePath();

            mRecorder.setOutputFile(outputPath);
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mRecorder.prepare();
            mRecorder.start();
        } catch (IOException e) {
            finishCanceled();
        }
    }

    @Override
    public void clickStopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        Intent data = new Intent();
        data.setData(Uri.fromFile(mView.getFile()));
        finishOk(data);
    }

    @Override
    public void deliverPermissionsResult(int requestCode, int[] grantResults) {
        boolean permissionGranted = false;

        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }

        if (!permissionGranted) {
            finishCanceled();
        }
    }

    private void finishCanceled() {
        mView.finish(Activity.RESULT_CANCELED, null);
    }

    private void finishOk(Intent data) {
        mView.finish(Activity.RESULT_OK, data);
    }
}