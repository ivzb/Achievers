package com.achievers.ui.voice_recording;

import android.content.Intent;

import com.achievers.databinding.VoiceRecordingFragBinding;
import com.achievers.ui._base.AbstractFragment;

public class VoiceRecordingFragment
        extends AbstractFragment<VoiceRecordingContract.Presenter, VoiceRecordingContract.ViewModel, VoiceRecordingFragBinding>
        implements VoiceRecordingContract.View<VoiceRecordingFragBinding> {

    @Override
    public void finish(int resultCode, Intent intent) {

    }
}