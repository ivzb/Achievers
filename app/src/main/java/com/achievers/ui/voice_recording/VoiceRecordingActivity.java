package com.achievers.ui.voice_recording;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.achievers.R;
import com.achievers.ui._base.AbstractActivity;
import com.achievers.utils.ActivityUtils;

public class VoiceRecordingActivity extends AbstractActivity {

    private MediaRecorder mRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_recording_act);

        // Set up the toolbar.
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setTitle(R.string.voice_recording);
        }

        VoiceRecordingFragment view =
                (VoiceRecordingFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);

        if (view == null) {
            view = new VoiceRecordingFragment();

            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(),
                    view,
                    R.id.contentFrame);
        }

        mRecorder = new MediaRecorder();

        view.setViewModel(new VoiceRecordingViewModel());
        view.setPresenter(new VoiceRecordingPresenter(
                this,
                view,
                mRecorder));
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }
}

