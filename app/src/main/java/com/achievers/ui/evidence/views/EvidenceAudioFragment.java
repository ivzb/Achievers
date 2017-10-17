package com.achievers.ui.evidence.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.databinding.EvidenceAudioFragBinding;
import com.achievers.ui.evidence.EvidenceFragment;

public class EvidenceAudioFragment extends EvidenceFragment<EvidenceAudioFragBinding> {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.evidence_audio_frag, container, false);

        mDataBinding = EvidenceAudioFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        return view;
    }
}