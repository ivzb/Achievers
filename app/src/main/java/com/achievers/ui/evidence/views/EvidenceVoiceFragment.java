package com.achievers.ui.evidence.views;

import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.VoiceMultimediaPlayer;

public class EvidenceVoiceFragment extends EvidenceExoPlayerFragment {

    @Override
    public void initMultimedia() {
        BaseMultimediaPlayer player = new VoiceMultimediaPlayer(
                mDataBinding.mvEvidence,
                getContext(),
                mExoPlayer,
                mViewModel.getEvidence().getUri());

        initMultimedia(player);
    }
}