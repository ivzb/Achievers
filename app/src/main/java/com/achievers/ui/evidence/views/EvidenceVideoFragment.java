package com.achievers.ui.evidence.views;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achievers.R;
import com.achievers.data.entities.Evidence;
import com.achievers.databinding.EvidenceVideoFragBinding;
import com.achievers.ui.evidence.EvidenceFragment;
import com.achievers.utils.ExoPlayerUtils;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VideoMultimediaPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;

public class EvidenceVideoFragment extends EvidenceFragment<EvidenceVideoFragBinding> {

    private SimpleExoPlayer mExoPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.evidence_video_frag, container, false);

        mDataBinding = EvidenceVideoFragBinding.bind(view);
        mDataBinding.setViewModel(mViewModel);

        mExoPlayer = ExoPlayerUtils.initialize(getContext());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Evidence evidence = mViewModel.getEvidence();
        Uri uri = Uri.parse(evidence.getUrl());

        BaseMultimediaPlayer player = new VideoMultimediaPlayer(
                mDataBinding.mvEvidence,
                getContext(),
                mExoPlayer,
                uri);

        // todo: catch if builder throws null pointer and show message
        mDataBinding.mvEvidence.builder(evidence.getMultimediaType())
                .withUrl(evidence.getPreviewUrl())
                .withPlayer(player)
                .build();
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
