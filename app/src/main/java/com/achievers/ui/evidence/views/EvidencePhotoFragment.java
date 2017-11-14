package com.achievers.ui.evidence.views;

import com.achievers.ui.evidence.EvidenceFragment;
import com.achievers.utils.ui.multimedia._base.BaseMultimediaPlayer;
import com.achievers.utils.ui.multimedia.players.PhotoMultimediaPlayer;

public class EvidencePhotoFragment extends EvidenceFragment {

    @Override
    public void initMultimedia() {
        BaseMultimediaPlayer player = new PhotoMultimediaPlayer(
                mDataBinding.mvEvidence);

        initMultimedia(player);
    }
}