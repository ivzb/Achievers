package com.achievers.ui.voice_recording;

import android.databinding.BaseObservable;

import java.io.File;

public class VoiceRecordingViewModel
        extends BaseObservable
        implements VoiceRecordingContract.ViewModel {

    @Override
    public File getFile() {
        return null;
    }

    @Override
    public void setFile(File file) {

    }
}