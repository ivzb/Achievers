package com.achievers.ui.evidence.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.ui.evidence.EvidenceFragment;
import com.achievers.utils.ExoPlayerUtils;
import com.google.android.exoplayer2.SimpleExoPlayer;

public abstract class EvidenceExoPlayerFragment extends EvidenceFragment {

    protected SimpleExoPlayer mExoPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mExoPlayer = ExoPlayerUtils.initialize(getContext());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();

        mDataBinding.mvEvidence.release();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        mExoPlayer.release();
        mExoPlayer = null;
    }
}