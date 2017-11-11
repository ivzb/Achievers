package com.achievers.ui.evidence.views;

import com.achievers.data.entities.Evidence;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VideoMultimediaPlayer;

public class EvidenceVideoFragment extends EvidenceExoPlayerFragment {

    @Override
    public void initMultimedia() {
        Evidence evidence = mViewModel.getEvidence();

        BaseMultimediaPlayer player = new VideoMultimediaPlayer(
                mDataBinding.mvEvidence,
                getContext(),
                mExoPlayer,
                evidence.getUri());

        // todo: catch if builder throws null pointer and show message
        mDataBinding.mvEvidence.builder(evidence.getMultimediaType())
                .withUrl(evidence.getPreviewUrl())
                .withPlayer(player)
                .build();
    }
}
