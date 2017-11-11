package com.achievers.ui.voice_recording;

import android.databinding.BaseObservable;

import java.io.File;

public class VoiceRecordingViewModel
        extends BaseObservable
        implements VoiceRecordingContract.ViewModel {

    private File mFile;

    @Override
    public File getFile() {
        return mFile;
    }

    @Override
    public void setFile(File file) {
        mFile = file;
    }
}