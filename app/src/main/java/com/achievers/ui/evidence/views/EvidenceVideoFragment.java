package com.achievers.ui.evidence.views;

import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VideoMultimediaPlayer;

public class EvidenceVideoFragment extends EvidenceExoPlayerFragment {

    @Override
    public void initMultimedia() {
        BaseMultimediaPlayer player = new VideoMultimediaPlayer(
                mDataBinding.mvEvidence,
                getContext(),
                mExoPlayer,
                mViewModel.getEvidence().getUri());

        initMultimedia(player);
    }
}