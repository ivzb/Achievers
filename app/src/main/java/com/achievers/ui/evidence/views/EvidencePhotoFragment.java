package com.achievers.ui.evidence.views;

import com.achievers.data.entities.Evidence;
import com.achievers.ui.evidence.EvidenceFragment;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.PhotoMultimediaPlayer;

public class EvidencePhotoFragment extends EvidenceFragment {

    @Override
    public void initMultimedia() {
        Evidence evidence = mViewModel.getEvidence();

        BaseMultimediaPlayer player = new PhotoMultimediaPlayer(
                mDataBinding.mvEvidence);

        // todo: catch if builder throws null pointer and show message
        mDataBinding.mvEvidence.builder(evidence.getMultimediaType())
                .withUri(evidence.getUri())
                .withPlayer(player)
                .build();
    }

    @Override
    public void onPause() {
        super.onPause();

        mDataBinding.mvEvidence.release();
    }
}