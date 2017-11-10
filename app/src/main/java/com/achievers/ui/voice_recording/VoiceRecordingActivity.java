package com.achievers.ui.voice_recording;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.achievers.R;
import com.achievers.utils.FileUtils;
import com.achievers.utils.ui.voice_recording.RecordActionHandler;
import com.achievers.utils.ui.voice_recording.RecordVoiceButton;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.achievers.utils.FileUtils.FileType.Voice;

public class VoiceRecordingActivity
        extends AppCompatActivity
        implements RecordActionHandler {

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    private File mFile;
    private MediaRecorder mRecorder;

    private boolean mPermissionToRecordAccepted;
    private String [] mPermissions = { Manifest.permission.RECORD_AUDIO };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recording_act);

        ActivityCompat.requestPermissions(this, mPermissions, REQUEST_RECORD_AUDIO_PERMISSION);

        try {
            mFile = FileUtils.createFile(this, new Date(), Voice);
        } catch (IOException e) {
            finishWith(Activity.RESULT_CANCELED, null);
        }

        RecordVoiceButton recordButton = findViewById(R.id.rvbRecordButton);
        recordButton.setOnRecordActionHandler(this);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            mPermissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }

        if (!mPermissionToRecordAccepted) {
            finishWith(Activity.RESULT_CANCELED, null);
        }
    }

    @Override
    public void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFile.getAbsolutePath());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            finishWith(Activity.RESULT_CANCELED, null);
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        Intent data = new Intent();
        data.setData(Uri.fromFile(mFile));
        finishWith(Activity.RESULT_OK, data);
    }

    private void finishWith(int resultCode, Intent data) {
        setResult(resultCode, data);
        finish();
    }
}