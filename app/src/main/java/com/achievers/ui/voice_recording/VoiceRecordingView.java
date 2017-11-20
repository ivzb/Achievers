package com.achievers.ui.voice_recording;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.databinding.VoiceRecordingFragBinding;
import com.achievers.ui._base.AbstractView;
import com.achievers.utils.ui.voice_recording.RecordActionHandler;

import java.io.File;

public class VoiceRecordingView
        extends AbstractView<VoiceRecordingContract.Presenter, VoiceRecordingContract.ViewModel, VoiceRecordingFragBinding>
        implements VoiceRecordingContract.View<VoiceRecordingFragBinding>, RecordActionHandler {

    public VoiceRecordingView() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.voice_recording_frag, container, false);

        mPresenter.requestRecordAudioPermission();

        mDataBinding = VoiceRecordingFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        mDataBinding.rvbRecordButton.setOnRecordActionHandler(this);

        return mDataBinding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        mPresenter.deliverPermissionsResult(requestCode, grantResults);
    }

    @Override
    public File getFile() {
        return mViewModel.getFile();
    }

    @Override
    public void setFile(File file) {
        mViewModel.setFile(file);
    }

    @Override
    public void onRecord(boolean start) {
        if (start) {
            mPresenter.clickStartRecording();
        } else {
            mPresenter.clickStopRecording();
        }
    }

    @Override
    public void finish(int resultCode, Intent intent) {
        getActivity().setResult(resultCode, intent);
        getActivity().finish();
    }
}